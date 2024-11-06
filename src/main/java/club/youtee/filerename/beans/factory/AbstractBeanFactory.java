package club.youtee.filerename.beans.factory;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

import club.youtee.filerename.beans.BeanInstantiationException;
import club.youtee.filerename.beans.BeansException;
import club.youtee.filerename.beans.annotation.Injector;

/**
 * @author Xinglong.Li
 * @date 2024-11-06
 */
public abstract class AbstractBeanFactory implements BeanFactory {

    /**
     * 已经创建Bean实例的集合
     */
    private final Map<Class<?>, Object> instances;

    /**
     * 通过获取其他对象注入创建Bean集合（通过获取已创建的bean的某个属性注入到需要创建的Bean中）
     */
    private final Map<Class<?>, Supplier<Object>> instancesLazyFromGetter;

    public AbstractBeanFactory() {
        instances = new ConcurrentHashMap<>();
        instancesLazyFromGetter = new ConcurrentHashMap<>();
    }

    /**
     * 重写注册对应的Bean
     */
    protected abstract void register() throws BeanInstantiationException;

    @Override
    public Object getBean(String name) throws BeansException {
        return null;
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return null;
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return null;
    }

    /**
     * 获取单例对象
     *
     * @param t 获取对象的类
     * @param <T>
     * @return
     */
    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        Supplier<?> getter = instancesLazyFromGetter.get(requiredType);
        return (T) (Objects.nonNull(getter) ? getter.get() : instances.get(requiredType));
    }

    @Override
    public <T> T getBean(Class<T> requiredType, Object... args) throws BeansException {
        return null;
    }

    /**
     * 通过构造函数注册Bean
     *
     * @param t
     * @param <T> registerFromConstructor(Bean.class)
     */
    public <T> void registerFromConstructor(Class<T> t) throws BeanInstantiationException {
        registerFromInstance(t, createBeanFromConstructor(t));
    }

    /**
     * 通过对象注册Bean
     *
     * @param t
     * @param instance
     * @param <T> registerFromConstructor(Bean.class, new Bean())
     */
    public <T> void registerFromInstance(Class<T> t, Object instance) {
        instances.put(t, instance);
    }

    /**
     * 通过Getter延迟拉取对象注册成Bean（Getter获取的对象可能是延迟构建的，所以需要延迟注册）
     *
     * @param t
     * @param getter
     * @param <T> registerFromConstructor(Bean.class, otherBean::getBean)
     */
    public <T> void registerFromGetter(Class<T> t, Supplier<T> getter) {
        instancesLazyFromGetter.put(t, (Supplier<Object>) getter);
    }

    /**
     * 通过Getter延迟拉取对象注册成Bean（Getter获取的对象可能是延迟构建的，所以需要延迟注册）
     *
     * @param t
     * @param s
     * @param getter
     * @param <T>
     * @param <S> registerFromConstructor(Bean.class, OtherBean.class, otherBean ->
     *            otherBean::getBean)
     */
    public <T, S> void registerFromGetter(Class<T> t, Class<S> s, Function<S, T> getter) {
        instancesLazyFromGetter.put(t, () -> getter.apply(getBean(s)));
    }

    /**
     * 通过构造函数创建Bean(非单例)
     *
     * @param t
     * @param <T>
     * @return
     * @throws BeanInstantiationException
     */
    public <T> T createBean(Class<T> t) {
        try {
            return createBeanFromConstructor(t);
        } catch (BeanInstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过构造函数创建Bean
     *
     * @param t
     * @param <T>
     * @return
     * @throws BeanInstantiationException
     */
    protected <T> T createBeanFromConstructor(Class<T> t) throws BeanInstantiationException {
        Constructor<T>[] cons = (Constructor<T>[]) t.getConstructors();
        // 可以构造对象的构造函数（优先使用@Injector的构造函数，没有则使用无参构造函数）
        Constructor<T> constructor = null;
        for (Constructor<T> c : cons) {
            if (c.getParameterCount() == 0) {
                constructor = c;
            } else if (c.isAnnotationPresent(Injector.class)) {
                constructor = c;
                break;
            }
        }

        if (Objects.isNull(constructor)) {
            throw new BeanInstantiationException(t, "没有使用@Injector注解构造函数，也没有无参的构造函数");
        }
        try {
            return constructor.newInstance(Arrays.stream(constructor.getParameterTypes()).map(this::getBean).toArray());
        } catch (Exception e) {
            throw new BeanInstantiationException(t, e.getMessage(), e);
        }
    }

}
