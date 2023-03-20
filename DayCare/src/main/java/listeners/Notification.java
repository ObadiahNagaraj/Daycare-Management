package listeners;

public class Notification implements NotificationForAdmin {

	@Override
	public void notifyAdmin() {
		System.out.println("new person Enrolled");
	}

}
