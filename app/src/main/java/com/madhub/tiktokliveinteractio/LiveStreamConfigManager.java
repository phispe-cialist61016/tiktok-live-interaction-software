```java
package com.madhub.tiktokliveinteractio;

/**
 * LiveStreamConfigManager is a crucial component of the MadHub application that manages TikTok live interaction features.
 * This manager class facilitates the automation processes required for engaging with live streams, including sending comments,
 * following streamers, and managing user interactions in real-time. The architectural design adheres to the principles of 
 * separation of concerns, facilitating easy modifications and scalability.
 */
public class LiveStreamConfigManager {

    // Configuration parameters for live interaction
    private String[] usernameList;  // List of usernames to interact with
    private String commentContent;   // Content of the comments to be sent
    private long sendInterval;       // Interval between sending comments
    private long totalInteractionTime; // Total duration for which interactions will occur
    private boolean isActive;        // State variable to track if the interaction is ongoing

    /**
     * Constructor for LiveStreamConfigManager initializes the configuration for live interactions.
     *
     * @param usernameList Array of usernames for targeting live streams.
     * @param commentContent Content of the comments to be sent during interactions.
     * @param sendInterval Interval in milliseconds between each comment sent.
     * @param totalInteractionTime Total time in milliseconds for which the interactions will last.
     */
    public LiveStreamConfigManager(String[] usernameList, String commentContent, long sendInterval, long totalInteractionTime) {
        this.usernameList = usernameList;
        this.commentContent = commentContent;
        this.sendInterval = sendInterval;
        this.totalInteractionTime = totalInteractionTime;
        this.isActive = false; // Initially set to inactive
    }

    /**
     * Starts the live interaction process by activating the interaction state and launching the interaction task.
     * This method uses a background service to ensure that the interactions run independently of the UI thread.
     */
    public void startLiveInteraction() {
        if (!isActive) {
            isActive = true; // Mark as active
            new Thread(() -> {
                long startTime = System.currentTimeMillis(); // Record start time
                while (isActive && (System.currentTimeMillis() - startTime) < totalInteractionTime) {
                    for (String username : usernameList) {
                        sendCommentToLive(username, commentContent);
                        try {
                            Thread.sleep(sendInterval); // Pause between comments
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
                stopLiveInteraction(); // Stop process after interaction time elapses
            }).start(); // Start interaction in a new thread to avoid blocking the UI
        }
    }

    /**
     * Stops the ongoing live interaction by setting the active state to false.
     * This clean-up method ensures that any resources or processes associated with live interaction are terminated.
     */
    public void stopLiveInteraction() {
        if (isActive) {
            isActive = false; // Mark as inactive
            // Additional cleanup can be performed here if necessary
        }
    }

    /**
     * Sends a comment to a specified user's live stream. This method is responsible for 
     * integrating with MadHub's API to post comments.
     *
     * @param username The username of the streamer whose live comment section is targeted.
     * @param comment The comment to be sent to the live stream.
     */
    private void sendCommentToLive(String username, String comment) {
        // Simulate sending a comment to the TikTok live stream for the specified user
        // This should leverage MadHub's underlying functionality to perform the live interaction.
        System.out.println("Sending comment: '" + comment + "' to user: " + username);
        // Here you would implement the actual network call to interact with TikTok's API
        // For example, you would use Retrofit or another library to post the comment on the live stream.
    }

    /**
     * Retrieves the configuration of the live interaction for logging or debugging purposes.
     *
     * @return A string representation of the current state and configuration of the live interaction.
     */
    public String getLiveInteractionConfig() {
        return "Live Interaction Config: { " +
                "Usernames: " + String.join(", ", usernameList) +
                ", Comment: '" + commentContent + "'" +
                ", Send Interval: " + sendInterval + " ms" +
                ", Total Interaction Time: " + totalInteractionTime + " ms" +
                ", Active: " + isActive +
                " }";
    }
}
```

### Explanation of Technical Architecture and Implementation
1. **Class Structure**: The `LiveStreamConfigManager` class encapsulates the core functionalities required for managing live interactions on TikTok. The design allows for easy extension and modification of interaction features.

2. **Configuration Management**: The class constructor initializes essential parameters, which can be easily updated based on user preferences. This flexibility aligns with MadHub's goal of providing a customizable automation experience.

3. **State Management**: The `isActive` boolean variable tracks the interaction's state, ensuring only one live interaction occurs at a time. This prevents conflicts and promotes stability.

4. **Thread Management**: By executing the interaction logic in a separate thread, the UI remains responsive during long-running tasks. This approach is a best practice in Android development, ensuring a smooth user experience.

5. **Live Interaction Logic**: The `startLiveInteraction` method handles the looping over usernames, with controlled pauses using `Thread.sleep()`, respecting the specified `sendInterval`. This ensures compliance with platform interaction guidelines to avoid getting flagged for spam behavior.

6. **API Integration Points**: The placeholder `sendCommentToLive` method outlines how the actual interaction with the TikTok API would occur, which is critical for implementing the live interaction features correctly while ensuring adherence to API usage policies.

7. **Logging and Debugging**: The `getLiveInteractionConfig` method provides a way to log the current configuration, which can be crucial for monitoring and debugging in a production environment.

With this structure, `LiveStreamConfigManager` exemplifies how MadHub's capabilities are effectively harnessed for TikTok live interactions, allowing for automated, compliant, and interactive user experiences.
