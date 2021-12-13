# Generic Locking-Library.
---
[![latest published version](https://badgen.net/maven/v/maven-central/one.jkr.de.jkrsoftware.entity.locking.libraries/Generic-Locking-Library)](https://s01.oss.sonatype.org/content/repositories/public/one/jkr/de/jkrsoftware/entity/locking/libraries/Generic-Locking-Library/)
[![last commit on main-branch](https://badgen.net/github/last-commit/jkrsoftware-de/Generic-Locking-Library/main)](https://github.com/jkrsoftware-de/Generic-Locking-Library/commit/main)
[![current watchers](https://badgen.net/github/watchers/jkrsoftware-de/Generic-Locking-Library)](https://github.com/jkrsoftware-de/Generic-Locking-Library/watchers)
[![given stars](https://badgen.net/github/stars/jkrsoftware-de/Generic-Locking-Library)](https://github.com/jkrsoftware-de/Generic-Locking-Library/stargazers)
---

## 💬 Contact Me.
If you need any help or just want to talk to me, have a look here: [contact-informations.md](contact-informations.md).

---

## 📕 About all of my Software-Products.
Just read the following Paragraphs. 😊

---

### 📃 I use the „Ports & Adapters“- / „Hexagonal“-Software-Design.
For every newly-created Software, I use the **„Ports & Adapters“-Design**.

For better understanding — what the **„Ports & Adapters“-Design** is — have a look here: [Wikipedia (EN) · Hexagonal Architecture](https://en.wikipedia.org/wiki/Hexagonal_architecture_(software))

---

### 📃 The underlying Software-License.
This Software is free forever.

There is nothing to pay and nothing to fulfill, to use/to edit/to (re-)publish my written Software-Code.

*You can also have a look at: [license.md](license.md) in the same Directory.*

---

## 📙 About the „Entity Locking“-Libraries.
I'm developing the Entity Locking-Libraries, cause in many of my current (and future) Software-Products, I need an 
easy Way to lock Entities, such as:
* Payment-Transactions for Reality.
* Payment-Transactions for own Gaming-Platforms.
* virtual Bank-Accounts for own Gaming-Platforms.
* User-Accounts

and so on.

The **Entity-Locking-Libraries** providing me (and potentially all other Humans) an **easy-(to-use/to-implement/to-understand) Way** to solve this Problem. :)

If you want to **switch your Lock-Backend**, you can easily switch to **another Abstraction-Variant of the Locking-Library**.

Every Library (except of the Root-Library itself) is depending on the „Generic-Locking-Library“.

**Why didn't I put everything just in one Library? :)**
<br />
This Expansion/Extension-Library-Concept was my first thought.
<br />
I loved this Idea.

I decided to design/develop the Libraries in this Way, cause:
* the Expandability/Understandability is much higher for the Outside-World
* and I've a good Foundation for further Development-Approaches.<br />
  Don't need to update the Core-Library, if I only want to develop a new Implementation.


---

### 📃 Why should I use one of these Libraries?
It's very easy to implement in your Application and easy to understand.

You can have an **Entity-Locking-Solution** right out of the Box for (currently) **_Plain Java_** and
**_Spring Boot-optimized_** Apps.

The Generic-Locking-Library is the Foundation of every Locking-Library developed by me.

You can use the **Generic-Locking-Library** in Combination with your own Lock-Backend-Implementation — or — you use my Implementations, in the Form of Extension-Libraries, such as:
* **DynamoDB-Locking-Library**
  * for _Spring Boot_-Apps: *DynamoDB-Locking-Library-SpringBoot-Starter*
* (coming soon) the **Redis-Locking-Library**
    * for _Spring Boot_-Apps: *Redis-Locking-Library-SpringBoot-Starter*

---