package ttl.larku.reflect.inject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class BeanFactory {
	/**
	 * Create an instance of the given type and do dependency injection 
	 * on it.
	 * 
	 * @param clazz
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static <T> T getBean(Class<T> clazz) throws InstantiationException, IllegalAccessException {
		T result = clazz.newInstance();
		
		doInjection(result);
		
		return result;
	}

	static final private Class<? extends Annotation> myInject = MyInject.class;
	public static void doInjection(Object target) throws InstantiationException, IllegalAccessException {
		Class<?> clazz = target.getClass();

		Field [] fields = clazz.getDeclaredFields();
		
		for(Field field : fields) {
			if(field.isAnnotationPresent(myInject)) {
				Class<?> targetType = field.getType();
				Object newInstance = targetType.newInstance();
				field.setAccessible(true);
				
				field.set(target, newInstance);
			}
		}
	}
}
