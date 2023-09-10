package vn.com.disruptorspring.disruptor.config;

import com.lmax.disruptor.dsl.Disruptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import vn.com.disruptorspring.config.StartupOrderConstants;
import vn.com.disruptorspring.disruptor.command.base.CommandDispatcher;
import vn.com.disruptorspring.lifecycle.DisruptorLifeCycleContainer;
import vn.com.disruptorspring.disruptor.command.base.*;
import vn.com.disruptorspring.disruptor.command.order.OrderInsertCommand;
import vn.com.disruptorspring.disruptor.command.order.OrderInsertCommandBuffer;
import vn.com.disruptorspring.disruptor.command.order.OrderInsertCommandExecutor;
import vn.com.disruptorspring.disruptor.command.order.OrderInsertCommandProcessor;
import vn.com.disruptorspring.utilities.BeanRegisterUtils;

import java.util.concurrent.Executors;

@Configuration
@EnableConfigurationProperties(OrderInsertProcessorConfiguration.Conf.class)
public class OrderInsertProcessorConfiguration implements ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderInsertProcessorConfiguration.class);

    @Autowired
    private Conf conf;

    @Autowired
    private CommandDispatcher commandDispatcher;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public OrderInsertCommandProcessor courseTakeInsertCmdProcessor() {
        LOGGER.info("Configure OrderInsertCommandProcessor");

        CommandEventProducer<OrderInsertCommand>[] commandEventProducerList = new CommandEventProducer[conf.getNum()];

        for (int i = 0; i < conf.getNum(); i++) {

            OrderInsertCommandBuffer cmdBuffer = new OrderInsertCommandBuffer(conf.getSqlBufferSize());
            OrderInsertCommandExecutor cmdExecutor = new OrderInsertCommandExecutor(jdbcTemplate);

            Disruptor<CommandEvent<OrderInsertCommand>> disruptor = new Disruptor<>(
                    new CommandEventFactory<>(),
                    conf.getQueueSize(),
                    Executors.defaultThreadFactory());

            disruptor
                    .handleEventsWith(new CommandEventDbHandler<>(cmdBuffer, cmdExecutor))
                    .then(new CommandEventGcHandler<>());

            // disruptor The exception handling is like this,
            // regardless of this form A->B, Still in this form A,B->C,D, Only the handler that throws an exception will interrupt execution.
            disruptor.setDefaultExceptionHandler(new CommandEventExceptionHandler<>());

            commandEventProducerList[i] = new CommandEventProducer<>(disruptor.getRingBuffer());

            BeanRegisterUtils.registerSingleton(
                    applicationContext,
                    "CommandEvent<OrderInsertCommand>_DisruptorLifeCycleContainer_" + i,
                    new DisruptorLifeCycleContainer<>("CommandEvent<OrderInsertCommand>_Disruptor_" + i, disruptor,
                            StartupOrderConstants.DISRUPTOR_ORDER_INSERT));
        }

        OrderInsertCommandProcessor cmdProcessor = new OrderInsertCommandProcessor(commandEventProducerList);

        commandDispatcher.registerCommandProcessor(cmdProcessor);

        return cmdProcessor;

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @ConfigurationProperties(prefix = "order-insert.proc")
    public static class Conf {

        /**
         * Number of processors
         */
        private int num;

        /**
         * Number of SQL statements executed at a time (Executing multiple SQL statements together is more efficient than executing them multiple times.)
         */
        private int sqlBufferSize;

        /**
         * disruptor queue length, Value must be a power of 2
         */
        private int queueSize;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getSqlBufferSize() {
            return sqlBufferSize;
        }

        public void setSqlBufferSize(int sqlBufferSize) {
            this.sqlBufferSize = sqlBufferSize;
        }

        public int getQueueSize() {
            return queueSize;
        }

        public void setQueueSize(int queueSize) {
            this.queueSize = queueSize;
        }

    }

}
