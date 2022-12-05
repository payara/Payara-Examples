# Realm-based Security Example

This example demonstrates a Jakarta EE Security based on realms. The realm used
is `file`, one of the default realms defined by Payara.

You can manage users using admin GUI. Go to admin gui: `http://localhost:4848`

Then go this way in the left menu: Configurations - server-config - Security - 
Realms - file. On this page, click `Manage User`.

For the purpose of this demo, add these two users:

* user `Adam`, group `user`, password (enter twice) `Eve`
* user `admin`, groups `admin,user`, password (enter twice) `5FsNqxmBug6x4eR`

It is possible to create the users also by the `asadmin` command. Create file
`password-adam` with this content:

    AS_ADMIN_USERPASSWORD=Eve

Run this command:

    ./asadmin --passwordfile=password-adam create-file-user --groups=user --authrealmname=file Adam

Similarly, do the same for admin with file `password-admin`:
    AS_ADMIN_USERPASSWORD=5FsNqxmBug6x4eR
    ./asadmin --passwordfile=password-admin create-file-user --groups=user:admin --authrealmname=file admin

User can be deleted by command

    ./asadmin delete-file-user --authrealmname=file admin

Testing POST for login:

    curl -i -H "Content-Type: application/json" -X POST -d '{"username":"Adam","password":"Eve"}' http://localhost:8080/realm-based-security-example-1.0-SNAPSHOT/rest/v1/security/login

Try also wrong password.
