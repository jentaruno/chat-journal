# Chat Journal

## An app that allows you to literally talk to the wall!

**What the application does**
- Record your journals in the form of texts
- Make multiple chats for various journal categories

**Who will use this project**
- People who want to journal, but cannot commit to writing long structured paragraphs

**Why I made this project**
- Journaling is good for reflection and letting out your emotions, but committing to it is difficult. I mostly reflect 
and let my thoughts out by talking to my best friend, but sometimes he can be pretty busy. So here's an app that has 
that rant format but nobody on the other side of the screen that would be bothered by it!
---
**User stories**
- Create and delete chat categories
- View a list of the chat categories you have
- Tap into a chat category and see the texts you have sent in it
- Type and send texts
- Save your Chat Journal to file (if I so choose)
- Load your Chat Journal from file (if I so choose)
- When you start the application, enter your name and automatically load your Chat Journal if it already exists.
---
**Possible future improvements**
- The biggest thing I could refactor is removing the *texts* field in ChatApp. The *texts* can be accessed through the
*category* field, and that would reduce coupling.
- Secondly, there are currently two functions that can add a new Category in CategoryList. One is called newChatCategory
  (takes a string and adds new Category with that title), and the other is called addChatCategory (takes a Category). 
Instead of naming them two different functions, it's possible to use overloading because they have the same end goal.
- Lastly, renaming the fields and comments would be helpful for
better consistency and readability. For example, as of now I sometimes call chats "chats", and sometimes I call them 
"chat categories".
---

***References***\
Data persistence code adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
