package com.github.barnabeepickle.anazm;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ANAZM implements ClientModInitializer {
	public static KeyBinding zoomKeyBinding;
	// For hooking the zoom event
	public static boolean isZooming;
	private static boolean originalSmoothCameraEnabled;
	private static final MinecraftClient mc = MinecraftClient.getInstance();

	public static final float zoomLevel = (float) 0.23;

	@Override
	public void onInitializeClient() {
		zoomKeyBinding = new KeyBinding("key.anazm.zoom", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_C, "category.anazm.main");
		
		isZooming = false;

		KeyBindingHelper.registerKeyBinding(zoomKeyBinding);
	}

	public static boolean isZooming() {
        return zoomKeyBinding.isPressed();
    }

    public static void toggleSmoothCamera() {
        if (zoomStarting()) {
            zoomStarted();
            enableSmoothCamera();
        }

        if (zoomStopping()) {
            zoomStopped();
            resetSmoothCamera();
        }
    }

	 private static boolean isSmoothCamera() {
        return mc.options.smoothCameraEnabled;
    }

    private static void enableSmoothCamera() {
        try {
			mc.options.smoothCameraEnabled = true;
		} catch (Exception e) {
			System.out.println("Failed to enable smoothCamera");
			e.printStackTrace();
		}
    }

    private static void disableSmoothCamera() {
        try {
			mc.options.smoothCameraEnabled = false;
		} catch (Exception e) {
			System.out.println("Failed to diable smoothCamera");
			e.printStackTrace();
		}
    }

	private static boolean zoomStarting() {
        return isZooming() && !isZooming;
    }

    private static boolean zoomStopping() {
        return !isZooming() && isZooming;
    }


    private static void zoomStarted() {
        originalSmoothCameraEnabled = isSmoothCamera();
        isZooming = true;
    }

	private static void zoomStopped() {
        isZooming = false;
    }


	private static void resetSmoothCamera() {
        if (originalSmoothCameraEnabled) {
            enableSmoothCamera();
        } else {
            disableSmoothCamera();
        }
    }
}
