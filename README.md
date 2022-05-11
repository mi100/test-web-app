#Main Page
|+|Avatar|Name|Phone|||
|---|---|---|---|---|---|
|1|😃|Dmytro Karhut|103|🖊️|❌|
|2|😃|Ivan Khmilevskiy|103|🖊️|❌|

when click on plus icon
a new page should be opened with create user form

when click on edit icon
an edit user page should be opened and user data should be filled in

when click on delete icon 
the record should be deleted

# Create/Edit Contact Page
<form>
    <label for="firstname">First Name <input name="firstname"></label><br/>
    <br/>
    <label for="lastname">Last Name <input name="lastname"></label><br/>
    <br/>
    <label for="phone">Phone Number <input name="phone"></label>
    <br/>
    <input type="submit" value="Save">
</form>

# URL
 - GET /contacts => main page
 - POST /contact => click on save btn on contact page in case of create
 - PUT /contact => click on save btn on contact page in case of edit
 - DELETE /contact => click on delete icon on main page
 - GET /contact/create => click on plus icon on main page
 - GET /contact/edit => click on edit icon on main page

#Tasks
21. F.I.R.S.T unit testing
25. JSTL
