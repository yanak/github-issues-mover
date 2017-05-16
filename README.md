# What is this?

The github-issues-mover moves issues in an old Github enterprise repository to a new one.

It simply gets issues by the [Github api](https://developer.github.com/v3/issues/) and creates same issues to a new repository.


# Generate package

```
$ sbt universal:packageBin
```

The generated package which name is like `github-issues-mover-0.0.1.zip` is in `target/universal`.


# Require paramters

```
--from <original-api-base-url>
--from-owner <original-owner>
--from-repo <original-repository-name>
--to <dist-api-base-url>
--to-owner <dist-owner>
--to-repo <dist-repository-name>
--from-id <your-original-login-id>
--from-password <your-original-login-passwod>
--to-id <your-dist-login-id>
--to-password <your-dist-login-password>
```


# Example

```
$ unzip github-issues-mover-0.0.1.zip
$ cd github-issues-mover-0.0.1.zip
$ bin/github-issues-mover --from 'http://original.exmaple.com/api/v3' --from-owner foo --from-repo awsome-repo --to http://dist.example.com/api/v3' --to-owner bar --to-repo awsome-repo --from-id your-github-enterprise-id --from-password 12345 --to-id your-github-enterprise-id --to-password 12345
```

