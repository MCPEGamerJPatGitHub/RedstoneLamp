/*
 * This file is part of RedstoneLamp.
 *
 * RedstoneLamp is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * RedstoneLamp is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with RedstoneLamp.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.redstonelamp.utils;

/**
 * Utility class that monitors the time a certain action has been running, and
 * executes a runnable if the time goes over the specified amount.
 *
 * @author RedstoneLamp Team
 */
public class MonitorStopwatch extends Thread {
    private long start;
    private long end;
    private final String taskName;
    private final long time;
    private final Runnable runnable;
    private boolean running = false;

    /**
     * Create a new stopwatch.
     * @param taskName The name of the task to be monitored.
     * @param millis The amount of milliseconds the task will be able to run for.
     * @param runnable The Runnable to be ran if the task runs over the specified amount.
     */
    public MonitorStopwatch(String taskName, long millis, Runnable runnable) {
        this.taskName = taskName;
        this.time = millis;
        this.runnable = runnable;
    }

    public void startMonitoring() {
        if(running) return;
        running = true;
        start = System.currentTimeMillis();
        start();
    }

    public void stopMonitoring() {
        if(!running) return;
        running = false;
        end = System.currentTimeMillis();
    }

    @Override
    public void run() {
        setName("MonitorThread-"+taskName);
        while(running) {
            long current = System.currentTimeMillis();
            if(current - start >= time) {
                runnable.run();
                break;
            }
        }
    }

    public long getEndTime() {
        return end;
    }
}
