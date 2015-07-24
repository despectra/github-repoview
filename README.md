# GitHub friends repositories viewer
## General description
This android app allows you to see the lists of your friends repositories
## Features
* Displaying list of your friends
* Showing the list of repositories of selected friend
* Showing the list of branches of selected repository
* All loaded data is cached so you can browse lists even without internet connection
* Simple material design theme
* Lists filtering feature using search input in appbar

## Workflow
Open app and enter your github login and password
![alt text](https://github.com/despectra/github-repoview/raw/master/screenshots/login.png "Login")

After that you will see the list of your friends (followers)
![alt text](https://github.com/despectra/github-repoview/raw/master/screenshots/friends_list.png "Friends")

Select any friend and app will show you his repos
![alt text](https://github.com/despectra/github-repoview/raw/master/screenshots/friend_repos_list.png "Repos")
![alt text](https://github.com/despectra/github-repoview/raw/master/screenshots/friend_repos_list2.png "Repos2")

Select repository from list and you will see the list of branches of this repo
![alt text](https://github.com/despectra/github-repoview/raw/master/screenshots/repo_branches_list.png "Branches")

You can also filter these lists using search button in appbar
![alt text](https://github.com/despectra/github-repoview/raw/master/screenshots/filtering.png "Filter")

## Architecture
1. All application activities uses **Loaders API** to load data from both network and cache
2. There are 2 types of loaders: local and network
  * **Local loaders** retrieves data from cache backed by Realm database
  * **Network loaders** do REST API calls using Retrofit
3. Each activity (apart from LoginActivity) has instances of these loaders and callbacks for them
4. When network loader retrieves a list of items it uses **CacheSyncManager** instance to refresh cache with newly retrieved items
5. When network loader completes request, it forces local loader to reload data
6. Both local and network loaders use the same data models classes thanks to the compatibility of Realm and Retrofit
7. Activities render only local loaders results. It uses **RecyclerView** and **ListAdapter** to display items

## Dependencies
This application uses a several external libraries:
* [**Retrofit**](http://square.github.io/retrofit/) - REST API implementation
* [**Realm**](http://realm.io) - local cache
* [**Android Support Library**](http://developer.android.com/tools/support-library/index.html) - material design widgets, styles
* [**Picasso**](http://square.github.io/picasso/) - loading user avatars
* [**Materialish-progress**](https://github.com/pnikosis/materialish-progress) - material progress wheel widget
