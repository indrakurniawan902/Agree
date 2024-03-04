# Gitflow For Developer

![gitflow developer](/assets/images/gitflow_developer.png)

## Step by Step

- Fork Upstream Repo's into your Origin Repo's
- Clone Repository into your local devices
- Add Upstream remote git on your project :

    ```bash title="Terminal"
    git remote add upstream ssh://git@gitlab.playcourt.id:31022/agree-logtan/mobile-services/agree-logtan-mobile-ecosystem.git
    ```

    !!! info
        we highly recommend using ssh instead of using https, you can setup ssh with this [Guide](https://docs.gitlab.com/ee/user/ssh.html)

- Create Branch feature branch names using hyphen-case, applied to features, bugfixes, and hotfixes
- Use backlog id for the commit messages
- Delete branches that merged into the develop branch, be it features, bugfixes, hotfixes
- Pulls new update from upstream
- Push develop branch to origin branch
- Merge request to upstream branch develop

    !!! info
        **Jenkins** will automatically run and build the apk if your Marge request has been approved and has been merged, and the apk results will be sent via Grub on Telegram.
