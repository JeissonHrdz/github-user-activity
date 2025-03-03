
# GitHub User Activity CLI

## Introduction
The **GitHub User Activity CLI** is a simple command-line application that fetches and displays GitHub activity events for a specified user. The application interacts with the GitHub API to retrieve various event types, allowing users to monitor GitHub activity conveniently.

## Table of Contents
- [Installation](#installation)
- [Usage](#usage)
- [Configuration](#configuration)
- [Examples](#examples)
- [Troubleshooting](#troubleshooting)
- [Url](#troubleshooting)


## Installation
1. Ensure you have **Java 8+** installed on your system.
2. Clone this repository:
   ```sh
   git clone https://github.com/JeissonHrdz/github-user-activity.git
   ```

3. Navigate to the project directory
  ```sh
cd github-activity-cli
 ```
4. Compile the Java files

```sh
javac Main.java Cli.java GitHubActivity.java
 ```
5. Run the program 
``` sh
java Main
 ```

## Usage
1. The program will prompt for a GitHub username.

2. After entering the username, the menu offers the following options:

* **List All Events:** Displays all recent events of the user.

* **List Specific Events:** Select a specific event type from the menu.

* **Change User:** Enter a different username to fetch their activity.

* **Exit:** Quit the application.

## Examples
**Example 1: Listing All Events**
```sh
Please Enter the username:
JeissonHrdz

Menu:
1. List All Events
2. List For Events
3. Change User
4. Exit
1

Type event: PushEvent
Repo name: JeissonHrdz/api-rest-express-mcv
Created at: 2025-03-03T16:26:13Z
<-- Commit --> 
Author: JeissonHrdz
Message: ADD: Query methods for get

..........................................................
Type event: PushEvent
Repo name: JeissonHrdz/github-user-activity
Created at: 2025-02-28T20:19:25Z
<-- Commit --> 
Author: JeissonHrdz
Message: ADD: Menu CLI
```
**Example 2: Filtering Events**
```sh
Select Option:
a. PushEvent
b. DeleteEvent
c. ForkEvent
...
Enter your choice: a

Type event: PushEvent
Repo name: JeissonHrdz/github-user-activity
Created at: 2025-02-28T20:19:25Z
<-- Commit --> 
Author: JeissonHrdz
Message: ADD: Menu CLI
```
## Troubleshooting

* **Issue: "This user not exist or not have activity"** Solution: Ensure the username is correct and the user has public activity.

* **Issue: "Not event activity"** Solution: The selected event type may not be present in the user's recent history.

## Url 

https://github.com/JeissonHrdz/github-user-activity




