# Usecase

Mainly, there's three use cases for each development

1. new feature
2. bugfix
3. hotfix

!!! info
    Case 1 only make it to develop branch. Case 2 and 3 make it to production

## Case 1

There's a developer will be developing a login feature. Things to do is:

- Create a new branch login feature with naming convention **feature-{Purpose}**

    ``` bash title="Terminal"
    git branch feature-login
    ```

- Checkout to the feature-login branch

    ``` bash title="Terminal"
    git checkout feature-login
    ```

- Do the development of the feature **(coding)**
- Pull first from the develop branch of upstream

    ``` bash title="Terminal"
    git pull upstream develop
    ```

- If there's conflict, resolve it first
- After that, do a commit with the new feature

    ```bash title="Terminal"
    git add -A
    git commit -m "add feature login #AGR-001"
    ```

- After commit, merge to develop branch
- Checkout to develop branch

    ```bash title="Terminal"
    git checkout develop
    ```

- Merge feature-login branch

    ```bash title="Terminal"
    git merge feature-login --no-ff
    ```

- Push develop branch to origin

    ```bash title="Terminal"
    git push origin develop
    ```

- Request merge from develop branch in origin to develop branch in upstream
- After merged, delete feature-login branch  

    ```bash title="Terminal"
    git branch -d feature-login
    ```

## Case 2 : Bugfix Development (There’s a Bug in Development)

There are 2 cases, namely:

- Bugs detected after Merge by Maintainer in Upstream repo
- Bugs detected after commit in Local repo

### Case 2.1

The developer developed the login feature and there are bugs after the merge in the Upstream repo. Things to do are:

- Make sure the position on the develop branch by:

    ```bash title="Terminal"
    git checkout develop
    ```

- Pull first from the develop upstream for the latest code updates

    ```bash title="Terminal"
    git pull upstream develop
    ```

- Create a bugfix branch of the feature with the bug example:

    ```bash title="Terminal"
    git branch bugfix-login
    ```

- Checkout into branch bugfix

    ``` bash title="Terminal"
    git checkout bugfix-login
    ```

- Perform bugfix work **(coding)**
- After that, don't forget to pull first from develop upstream by doing this

    ```bash title="Terminal"
    git pull upstream develop
    ```

- After finishing commit to the results of the development bug

    ```bash title="Terminal"
    git add -A
    git commit -m "bugfix login #AGR-001"
    ```

- After the commit, then we will merge to the develop branch by Checkout into branch develop

    ```bash title="Terminal"
    git checkout develop
    ```

- Merge branch feature login

    ```bash title="Terminal"
    git merge bugfix-login --no-ff
    ```

- Push into branch develop

    ```bash title="Terminal"
    git push origin develop
    ```

- After that do Merge Request from Develop Origin to Develop Upstream
- Don't forget after that the feature branch is deleted by:

    ```bash title="Terminal"
    git branch -d bugfix-login
    ```

### Case 2.2

The developer developed the register feature and there are bugs after committing in the Local repo. Thing
what is done is:

- Make sure the position on the develop branch by:

    ```bash title="Terminal"
    git checkout develop
    ```

- Pull first from the develop upstream for the latest code updates

    ```bash title="Terminal"
    git pull upstream develop
    ```

- For example, a developer makes a branch feature register, for example:

    ```bash title="Terminal"
    git branch feature-register
    ```

- Checkout to feature-register branch

    ```bash title="Terminal"
    git checkout feature-register
    ```

- Perform registration features **(coding)**
- Pull first from develop upstream

    ```bash title="Terminal"
    git pull upstream develop
    ```

- Commit the results of the register feature

    ```bash title="Terminal"
    git add -A
    git commit -m "add feature register #AGR-002"
    ```

- After the commit, it seems that there are bugs, so what you have to do is continue to merge to
 develop first by means of

    ```bash title="Terminal"
    git checkout develop
    git merge feature-register --no-ff
    ```

- Then after the merge, create a new branch called bugfix with the name bugfix-register

    ```bash title="Terminal"
    git branch bugfix-register
    ```

- Checkout to branch bugfix

    ```bash title="Terminal"
    git checkout bugfix-register
    ```

- Perform bugfix work **(coding)**
- After that, don't forget to pull first from develop upstream

    ```bash title="Terminal"
    git pull upstream develop
    ```

- After completing the commit on the results of the development bug

    ```bash title="Terminal"
    git add -A
    git commit -m "bugfix register #AGR-002"
    ```

- After the commit, then we will merge to the develop branch by Checkout to develop branch

    ```bash title="Terminal"
    git checkout develop
    ```

- Merge branch login feature

    ```bash title="Terminal"
    git merge bugfix-register --no-ff
    ```

- Push to develop branch

    ```bash title="Terminal"
    git push origin develop
    ```

- After that do Merge Request from Develop Origin to Develop Upstream
- Don't forget after that the feature branch is deleted by:

    ```bash title="Terminal"
    git branch -d feature-register
    git branch -d bugfix-register
    ```

## Case 3 : Hotfix Development (There’s a Bug in Production)

How did this case happen? This case occurs when bugs occur after the code upgrades to
production. The solution is to do something called Hotfix. An example is there are bugs
registers in production.

- Make sure the position is in the Master branch

    ```bash title="Terminal"
    git checkout master
    ```

- Pull All Branches from master (to get all upstream branches)

    ```bash title="Terminal"
    git pull upstream
    ```

- Do Pull first from Upstream Master

    ```bash title="Terminal"
    git pull upstream master
    ```

- Create Branch Hotfix with feature name

    ```bash title="Terminal"
    git branch hotfix-register
    ```

- Checkout to Hotfix branch

    ```bash title="Terminal"
    git checkout hotfix-register
    ```

- Perform hotfix activities **(coding)**
- Pull first from upstream master

    ```bash title="Terminal"
    git pull upstream master
    ```

- Commit the work

    ```bash title="Terminal"
    git add -A
    git commit -m "hotfix register #AGR-002"
    ```

- Checkout to Branch release (The result of pulling all branches from the Upstream repo, not created by yourself)

    ```bash title="Terminal"
    git checkout release
    ```

- Merge to branch release

    ```bash title="Terminal"
    git merge hotfix-register --no-ff
    ```

- Push to branch release

    ```bash title="Terminal"
    git push origin release
    ```

- Perform Merge Request from RELEASE origin to RELEASE upstream
- Don't forget to delete the hotfix branch

    ```bash title="Terminal"
    git branch -d hotfix-register
    ```
