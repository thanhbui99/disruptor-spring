package vn.com.disruptorspring.config.core;

import com.lmax.disruptor.dsl.Disruptor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.com.disruptorspring.config.StartupOrderConstants;
import vn.com.disruptorspring.config.itl.CommandDispatcher;
import vn.com.disruptorspring.config.itl.DefaultCommandDispatcher;
import vn.com.disruptorspring.config.lifecycle.DisruptorLifeCycleContainer;
import vn.com.disruptorspring.memdb.ItemRepository;
import vn.com.disruptorspring.request.*;
import vn.com.disruptorspring.utilities.BeanRegisterUtils;


import java.util.concurrent.Executors;

@Configuration
@EnableConfigurationProperties({RequestDisruptorConfiguration.DisruptorProperties.class})
public class RequestDisruptorConfiguration implements ApplicationContextAware {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DisruptorProperties disruptorProperties;

    @Bean
    public CommandDispatcher commandDispatcher() {
        return new DefaultCommandDispatcher();
    }

    @Bean
    public RequestDtoEventProducer requestDtoEventProducer() {

        RequestDtoEventDbOutputer requestDtoEventDbOutputer = new RequestDtoEventDbOutputer();
        requestDtoEventDbOutputer.setCommandDispatcher(commandDispatcher());

        RequestDtoEventJmsOutputer requestDtoEventJmsOutputer = new RequestDtoEventJmsOutputer();

        Disruptor<RequestDtoEvent> disruptor = new Disruptor<>(
                new RequestDtoEventFactory(),
                disruptorProperties.getJvmQueueSize(),
                Executors.defaultThreadFactory()
        );

        disruptor
                .handleEventsWith(requestDtoEventBusinessHandler())
                .then(requestDtoEventJmsOutputer, requestDtoEventDbOutputer)
                .then(new RequestDtoEventGcHandler());

        // disruptor The exception handling is like this,
        // regardless of this form A->B, Still in this form A,B->C,D, Only the handler that throws an exception will interrupt execution.
        disruptor.setDefaultExceptionHandler(new RequestDtoEventExceptionHandler());

        RequestDtoEventProducer requestDtoEventProducer = new RequestDtoEventProducer(disruptor.getRingBuffer());

        BeanRegisterUtils.registerSingleton(
                applicationContext,
                "RequestDtoEventDisruptorLifeCycleContainer",
                new DisruptorLifeCycleContainer<>("RequestDtoEventDisruptor", disruptor, StartupOrderConstants.DISRUPTOR_REQUEST_DTO));

        return requestDtoEventProducer;
    }

    private RequestDtoEventBusinessHandler requestDtoEventBusinessHandler() {
        RequestDtoEventBusinessHandler requestDtoEventBusinessHandler = new RequestDtoEventBusinessHandler();
        requestDtoEventBusinessHandler.setItemRepository(applicationContext.getBean(ItemRepository.class));
        return requestDtoEventBusinessHandler;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @ConfigurationProperties(prefix = "request-disruptor")
    public static class DisruptorProperties {

        private int jvmQueueSize;

        public int getJvmQueueSize() {
            return jvmQueueSize;
        }

        public void setJvmQueueSize(int jvmQueueSize) {
            this.jvmQueueSize = jvmQueueSize;
        }

    }

}
