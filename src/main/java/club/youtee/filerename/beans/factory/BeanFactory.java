package club.youtee.filerename.beans.factory;

import club.youtee.filerename.beans.BeansException;

/**
 * @author Xinglong.Li
 * @date 2024-11-06
 */
public interface BeanFactory {

    Object getBean(String name) throws BeansException;

    <T> T getBean(String name, Class<T> requiredType) throws BeansException;

    Object getBean(String name, Object... args) throws BeansException;

    <T> T getBean(Class<T> requiredType) throws BeansException;

    <T> T getBean(Class<T> requiredType, Object... args) throws BeansException;
}
