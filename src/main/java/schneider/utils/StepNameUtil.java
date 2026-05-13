package schneider.utils;

import java.lang.reflect.Method;

public class StepNameUtil {

	public static String getStepName() {

		
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();

		try {

			for (StackTraceElement element : stack) {

				String className = element.getClassName();

				if (className.contains("pageobjects")) {

					Class<?> clazz = Class.forName(className);

					for (Method method : clazz.getDeclaredMethods()) {

						if (method.getName().equals(element.getMethodName())) {

							if (method.isAnnotationPresent(StepName.class)) {

								return method.getAnnotation(StepName.class).value();
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "Executing Step";
	}
}
