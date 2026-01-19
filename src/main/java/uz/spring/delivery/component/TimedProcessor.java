package uz.spring.delivery.component;

import org.aopalliance.intercept.MethodInterceptor;
import org.jspecify.annotations.Nullable;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

@Component
public class TimedProcessor implements BeanPostProcessor {

    @Override
    public @Nullable Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        for (var method : bean.getClass().getMethods()) {
            if (AnnotationUtils.findAnnotation(method, Timed.class) != null) {
                return createProxy(bean);
            }
        }

        return bean;
    }

    private Object createProxy(Object bean) {
        ProxyFactory proxyFactory = new ProxyFactory(bean);
        proxyFactory.addAdvice((MethodInterceptor) methodInvocation -> {
            var start = System.currentTimeMillis();
            try {
                return methodInvocation.proceed();
            } finally {
                var end = System.currentTimeMillis();
                System.out.println(methodInvocation.getMethod().getName() + " " + (end - start) + " ms");
            }
        });
        return proxyFactory.getProxy();
    }

}
