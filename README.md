# goose-patrol README
## How to use LibGDX
Look around on your favorite search engine to find information on how to use LibGDX (the game engine we're using), here are some places to start:
- Tutorials endorsed by LibGDX: https://libgdx.com/wiki/start/demos-and-tutorials
- A simple tutorial aimed at people who have never done game development: https://colourtann.github7.io/HelloLibgdx/

## How we will work on code as a team
I recommend using the Java IDE `IntelliJ Idea` as it is very powerful, very intuitive, and it's very easy to import this project into it.

If you are using IntelliJ, open it to get to the welcome screen, select "clone repository", select GitHub from the sidebar (not GitHub Enterprise), 
log in with your GitHub account, at which point you should be able to select `goose-patrol` from the list, then click `clone` and (after a lengthy importing process)
everything should just work! Go to `lwjgl3/src/main/java/io/github/goosePatrol/lwjgl3/Lwjgl3Launcher.java`, open the file, and click the big green play button in the
top right to make sure everything works.

Now, go to the top of the screen, where you should see a weird, vaguely branch-looking icon and the word `main`. Click on this drop-down and, if your development branch
has already been created, select `remote`, then the branch with your name, and click `checkout`. You can now develop on your branch and create a pull request when you are
ready to push your changes. Otherwise, click `new branch`, name the branch your name, make sure to leave `checkout branch` **checked**, then click create branch. 

You should **never** work on the main branch after this.

At the moment, your branch only exists locally, i.e., only on your own computer, you won't be able to continue your development work from another computer, and our 
teammates won't be able to snoop on what you're doing. To fix this, click on the git icon (it looks like the branch icon from earlier) in the very, very bottom left 
of the screen. This will open the git bottom bar, where you can see lots of information about all the commits that have been made. Ignore that for now, right-click on your named
branch (under the `local` drop-down), click `push`, then `push` again, and now your branch is visible to everyone in the team!

The basic development workflow will be like this:
1. Right click origin/main and click `pull into branch <your-name> using merge`. Make a habit of doing this very very often! Otherwise, your code will not be up to date with the main branch
   and horrible things can happen.
2. Make changes to the code (keep to your section of the code, don't encroach on other people's code, lest we have to deal with some awful merge conflicts later!)
3. Make commits as often as you like to save your progress.
4. Once you have finished a feature and made SURE that your code is in good working state by testing on your machine, push your changes (if they weren't pushed already).
   Now, go to github.com in your browser, go to the goose-patrol repository, select your named branch from the branch drop-down, at which point you'll probably see a big green button
   labelled `compare and pull request`. Click this, make sure you don't have any glaring merge conflicts (the banner at the top will tell you), then give your pull request a good title
   and description so everyone knows what you added, and click `create pull request`.
5. Wait for Kass to review your pull request and, assuming everything is alright, merge it into the main branch. If there are issues with your pull request (such as bad merge conflicts or
   maybe your code style/commenting isn't up to snuff), Kass will leave a comment on your pull request telling you what you need to fix before your pull request can be merged.
6. Repeat until our masterpiece is done!

I will also take this opportunity to say:
**No AI code.** You can consult AI for help if you want to, but all the code you push should be hand-written (and not copied from AI, of course). I will not accept any pull requests with 
AI-generated code. It is quite easy to tell.
