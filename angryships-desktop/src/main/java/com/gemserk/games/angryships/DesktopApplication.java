package com.gemserk.games.angryships;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gemserk.commons.adwhirl.AdWhirlViewHandler;
import com.gemserk.commons.gdx.DensityUtils.Density;
import com.gemserk.commons.gdx.DensityUtilsFixedImpl;

public class DesktopApplication {

	protected static final Logger logger = LoggerFactory.getLogger(DesktopApplication.class);

	private static class Arguments {

		int width = 800;
		int height = 480;

		public void parse(String[] argv) {
			if (argv.length == 0)
				return;

			String displayString = argv[0];
			String[] displayValues = displayString.split("x");

			if (displayValues.length < 2)
				return;

			try {
				width = Integer.parseInt(displayValues[0]);
				height = Integer.parseInt(displayValues[1]);
			} catch (NumberFormatException e) {
				System.out.println("error when parsing resolution from arguments: " + displayString);
			}

		}

	}

	public static void main(String[] argv) {

		Arguments arguments = new Arguments();
		arguments.parse(argv);

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Gemserk's Angry Ships";
		config.width = arguments.width;
		config.height = arguments.height;
		config.fullscreen = false;
		config.useGL20 = false;
		config.useCPUSynch = true;
		config.forceExit = true;
		config.vSyncEnabled = true;

		Density density = Density.ExtraHigh;
		if (config.width <= 320)
			density = Density.Low;
		else if (config.width <= 480)
			density = Density.Medium;
		else if (config.width <= 800)
			density = Density.High;
		else 
			density = Density.ExtraHigh;

		Game game = new Game();

		game.setAdWhirlViewHandler(new AdWhirlViewHandler());

		game.setDensityUtils(new DensityUtilsFixedImpl(density));

		boolean runningInDebug = System.getProperty("runningInDebug") != null;

		new LwjglApplication(game, config);
	}

}
