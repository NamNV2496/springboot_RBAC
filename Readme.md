# RBAC

```yml
spring:
  messages:
    basename: i18n/messages
```

we need parse token and save username and role to securityContextHolder

```text
SecurityContextHolder.getContext().setAuthentication(authentication);
```

`@PreAuthorize("hasAuthority('admin')")` or `@PreAuthorize("hasAuthority('admin') || hasAuthority('user')")` will access and get role to compare automatically

```textmate
Role does not access

curl --location --request GET 'http://localhost:8080/getSecureUser' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTY2NDQ2MjEyOCwiZXhwIjoxNjY0NDY1NzI4LCJkYXRhIjpbeyJyb2xlcyI6WyJhZG1pbiIsIm1lbWJlciJdfV19.YrwB4s_pe6Gg9GwwFhVGv3JW7AumivKLGxFudSMNDRM' \
--header 'Cookie: JSESSIONID=45F56EEF7853451443C13E5B6F6458D3' \
--data-raw ''

Role access

curl --location --request GET 'http://localhost:8080/getSecure' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTY2NDQ2MjEyOCwiZXhwIjoxNjY0NDY1NzI4LCJkYXRhIjpbeyJyb2xlcyI6WyJhZG1pbiIsIm1lbWJlciJdfV19.YrwB4s_pe6Gg9GwwFhVGv3JW7AumivKLGxFudSMNDRM' \
--header 'Cookie: JSESSIONID=45F56EEF7853451443C13E5B6F6458D3' \
--data-raw ''
```

# Way 2 use AOP

https://github.com/NamNV2496/Annotation
