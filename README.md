# Reddit

This is an Android mobile app that mocks [reddit](https://www.reddit.com/) threads where users can add, search, and delete messages and sub-messages through a Firebase database.

The following functionality is **complete**:

* [X] Connect to Firebase to store all of the Message information
* [X] Post a message to the thread
* [X] Be able to reply to a message, and that sub-message is indented to show a hierarchy. 
* [X] Implement two levels of messages: parent posts, and their replies
* [X] Each message should have a score
* [X] Ability to upvote (increase a score) and downvote (decrease a score)
* [X] Ability to upvote (increase a score) and downvote (decrease a score)
* [X] Flag option next to each message (every message regardless of their hierarchy) that allows a user to delete the message. If the user deletes a post, it should delete the post and all of the sub-posts. 

The following **optional** features could be implemented:
* Sort messages by score
* Allow users to add images

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

<img src='http://g.recordit.co/J4c2dFJBWO.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [Recordit](https://recordit.co/).

## Installation

Download:
* [Android Studio](https://developer.android.com/studio)

Create an account on:
* [Firebase](https://firebase.google.com/)

In order to connect the two, open the assistant window on Android Studio, add a Realtime database, and making sure your permission rules on edit and read are set to true on Firebase. To use the Assistant Window, you will need Google Repository v26 or higher downloaded in Android Studio.

## Usage

Press the green play button at the top of your left side on Android Studio. Check if the data is entered successfully on the Firebase website.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
 
