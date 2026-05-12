package schneider.assertions;

import org.testng.Assert;
import schneider.utils.ToastUtils;

public class ToastAssertions {
	public static void assertToastSuccess(ToastUtils toast, String expectedMessage) {

		Assert.assertEquals(toast.getToastType(), "success", "Toast type mismatch (expected success)");

		Assert.assertEquals(toast.getToastMessage(), expectedMessage, "Success toast message mismatch");
	}

	public static void assertToastError(ToastUtils toast, String expectedMessage) {

		Assert.assertEquals(toast.getToastType(), "error", "Toast type mismatch (expected error)");

		Assert.assertEquals(toast.getToastMessage(), expectedMessage, "Error toast message mismatch");
	}
}
