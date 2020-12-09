package io.github.martinheywang.products.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import io.github.martinheywang.products.api.model.action.Action;
import io.github.martinheywang.products.api.model.device.Device;
import io.github.martinheywang.products.api.model.exception.MoneyException;

public final class LoopController {

    private final GameController controller;

    private Integer delay;

    private Thread thread;
    private Loop gameLoop;

    public LoopController(GameController controller) {
        this.controller = controller;

        this.delay = controller.getGame().getDelay();

        this.gameLoop = new Loop();
        this.thread = new Thread(this.gameLoop);
    }

    public void start() {
        gameLoop.setAutoMode(true);
        thread.start();
    }

    public void stop() {
        gameLoop.setAutoMode(false);
        // the game loop checks if auto is enabled
        // if not the game loop stops, so the thread. 
    }

    /**
     * Returns the delay applied between each iteration of the game loop.
     * 
     * @return the delay.
     */
    public int getDelay() {
        return delay;
    }

    class Loop implements Runnable {

        private volatile boolean autoMode = true;

        @Override
        public void run() {
            do {
                try {
                    controller.deviceController().clearIterations();
                    for (final Device independent : controller.deviceController().getIndependentDevices())
                        for (int i = 0; i < independent.getLevel().getValue(); i++) {
                            final List<Device> toPulse = new ArrayList<>();
                            BigInteger cost = BigInteger.ZERO;

                            // The independent device is a device at the beginning of the assembly line; it
                            // doesn't any resources in order to work
                            Action action = independent.act(null);
                            while (action.isSuccessful()) {
                                toPulse.add(action.getExecutor());
                                cost = cost.add(action.getCost());
                                action.getExecutor().getCurrentIteration().addAction(action);

                                // The device did not give any valid pack.
                                if (action.getGivenPack() == null)
                                    break;

                                // The device did not give an output, or this output does not allow a connection
                                // with the next device.
                                if (action.getOutput() == null)
                                    break;
                                if (!controller.connectionExists(action.getPosition(), action.getOutput()))
                                    break;

                                final Device next = controller.getDevice(action.getOutput());

                                // The next device is overload, it cannot do
                                // anything
                                if (!DeviceController.isActReady(next))
                                    break;

                                // Find the next device in the assembly line and make it act
                                action = next.act(action.getGivenPack());

                            }

                            for (final Device device : toPulse) {
                                controller.pulseDevice(device);
                            }
                            controller.removeMoney(cost);
                            controller.moneyController().registerChange();
                        }
                } catch (final MoneyException e) {
                    e.printStackTrace();
                }

                // Wait a little bit of course
                try {
                    Thread.sleep(delay);
                } catch (final InterruptedException e) {
                    // Thread was interrupted
                    e.printStackTrace();
                }
            } while (this.autoMode);
        }

        public void setAutoMode(boolean mode) {
            this.autoMode = mode;
        }
    }
}
