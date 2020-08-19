package br.com.squadra.ssd.datatables;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Id;
import java.lang.reflect.Field;

@Getter @Setter
public class DataTableUtility<T> {
	private T object;
	private Class<?> clazz;

	public DataTableUtility(T object) {
		this.object = object;
		this.clazz = this.object.getClass();
	}

	public boolean setValue(String fieldName, String fieldValue) {
		if(fieldName.equals("serialVersionUID"))
			return false;

		if (isPrimitiveType(fieldName, fieldValue))
			return true;
		else if (isComplexType(fieldName, fieldValue))
			return true;

		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean isPrimitiveType(String fieldName, String fieldValue) {
		if (!fieldName.contains(".")) {
			try {
				Field field = getField(fieldName);
				if (field.isAnnotationPresent(Id.class) || field.isAnnotationPresent(EmbeddedId.class))
					return false;

				field.setAccessible(true);

				String fieldType = field.getType().getSimpleName();
				switch (fieldType) {
				case "String":
					field.set(object, fieldValue);
					break;
				case "byte":
					field.set(object, Byte.parseByte(fieldValue));
					break;
				case "int":
					field.set(object, Integer.parseInt(fieldValue));
					break;
				case "long":
					field.set(object, Long.parseLong(fieldValue));
					break;
				case "double":
					field.set(object, Double.parseDouble(fieldValue.replace(",", ".")));
					break;
				case "float":
					field.set(object, Float.parseFloat(fieldValue.replace(",", ".")));
					break;
				default:
					if (field.isEnumConstant()) {
						field.set(object, Enum.valueOf((Class<Enum>) field.getType(), fieldValue));
					} else
						return false;
				}

				return true;
			} catch (NoSuchFieldException e) {
				System.err.println("NoSuchFieldException: Não foi possível realizar reflection do campo '" + fieldName
						+ "' com o valor '" + fieldValue + "'");
			} catch (SecurityException e) {
				System.err.println("SecurityException: Não foi possível realizar reflection do campo '" + fieldName
						+ "' com o valor '" + fieldValue + "'");
			} catch (IllegalAccessException e) {
				System.err.println("IllegalAccessException: Não foi possível realizar reflection do campo '" + fieldName
						+ "' com o valor '" + fieldValue + "'");
			} catch (NumberFormatException e) {
				System.err.println("NumberFormatException: Não foi possível realizar reflection do campo '" + fieldName
						+ "' com o valor '" + fieldValue + "'");
			}
		}

		return false;
	}

	private boolean isComplexType(String fieldName, String fieldValue) {
		if (!fieldName.contains(".")) {
			try {
				Field field = getField(fieldName);
				if (field.isAnnotationPresent(Id.class) || field.isAnnotationPresent(EmbeddedId.class))
					return false;
				Class<?> childClass = field.getType();

				DataTableUtility<?> reflectionUtilityChild = new DataTableUtility<>(childClass.newInstance());
				for (Field f : childClass.getDeclaredFields()) {
					reflectionUtilityChild.setValue(f.getName(), fieldValue);
				}

				field.setAccessible(true);
				field.set(object, reflectionUtilityChild.getObject());

				return true;
			} catch (NoSuchFieldException e) {
				System.err.println("NoSuchFieldException: Não foi possível realizar reflection do campo '" + fieldName
						+ "' com o valor '" + fieldValue + "'");
			} catch (SecurityException e) {
				System.err.println("SecurityException: Não foi possível realizar reflection do campo '" + fieldName
						+ "' com o valor '" + fieldValue + "'");
			} catch (IllegalAccessException e) {
				System.err.println("IllegalAccessException: Não foi possível realizar reflection do campo '" + fieldName
						+ "' com o valor '" + fieldValue + "'");
			} catch (NumberFormatException e) {
				System.err.println("NumberFormatException: Não foi possível realizar reflection do campo '" + fieldName
						+ "' com o valor '" + fieldValue + "'");
			} catch (InstantiationException e) {
				System.err.println("InstantiationException: Não foi possível realizar reflection do campo '" + fieldName
						+ "' com o valor '" + fieldValue + "'");
			}
		}
		return false;
	}

	private Field getField(String fieldName) throws NoSuchFieldException, SecurityException {
		try {
			return clazz.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			try {
				return clazz.getSuperclass().getDeclaredField(fieldName);
			} catch (NoSuchFieldException | SecurityException exception) {
				throw exception;
			}
		}
	}
}