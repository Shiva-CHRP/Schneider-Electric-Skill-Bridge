package schneider.assertions;

import org.testng.Assert;

import schneider.utils.ToastResponse;
import schneider.utils.ToastUtils;

public class ToastAssertions {
	public static void assertToastSuccess(ToastResponse toast, String expectedMessage, String expectedType) {

		Assert.assertEquals(toast.getMessage(), expectedMessage, "Toast type mismatch (expected success)");

		Assert.assertEquals(toast.getType(), "success",  "Success toast message mismatch");
	}

	public static void assertToastError(ToastResponse toast, String expectedMessage, String expectedType) {

		Assert.assertEquals(toast.getMessage(), expectedMessage, "Toast type mismatch (expected error)");

		Assert.assertEquals(toast.getType(),"error",  "Error toast message mismatch");
	}
}
