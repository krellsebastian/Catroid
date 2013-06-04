package org.catrobat.catroid.uitest.tooltipsystem;

import org.catrobat.catroid.R;
import org.catrobat.catroid.tooltipsystem.Tooltip;
import org.catrobat.catroid.ui.MainMenuActivity;
import org.catrobat.catroid.uitest.util.UiTestUtils;

import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

public class TooltipInMainMenuTest extends ActivityInstrumentationTestCase2<MainMenuActivity> {

	private Solo solo;

	public TooltipInMainMenuTest() {
		super(MainMenuActivity.class);
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
		UiTestUtils.clearAllUtilTestProjects();
		super.tearDown();
		solo = null;
	}

	public void testTooltipSystem() {
		solo.waitForActivity(MainMenuActivity.class.getSimpleName());
		solo.clickOnActionBarItem(R.id.button_tooltip);

		solo.sleep(200);

		Tooltip tooltip = Tooltip.getInstance();
		assertNotNull("The tooltip system is null", tooltip);
		assertTrue("the tooltip system is active", MainMenuActivity.tooltipActive);

		//check if the tooltip button is shown on all positions

		solo.clickOnScreen(410, 160);
		solo.sleep(200);
		//überprüfe ob bubble angezeigt wird
		//bubble wurde auf tooltiplayer gezeichnet und kann nicht mehr abgerufen werden
		//wie kann ich dann überprüfen ob die bubble angezeigt wird???????

		solo.clickOnScreen(410, 310);
		solo.sleep(200);
		//überprüfe ob bubble angezeigt wird

		solo.clickOnScreen(410, 410);
		solo.sleep(200);
		//überprüfe ob bubble angezeigt wird

		solo.clickOnScreen(410, 510);
		solo.sleep(200);
		//überprüfe ob bubble angezeigt wird

		solo.clickOnScreen(410, 610);
		solo.sleep(200);
		//überprüfe ob bubble angezeigt wird

		solo.clickOnScreen(410, 710);
		solo.sleep(200);
		//überprüfe ob bubble angezeigt wird

	}
}