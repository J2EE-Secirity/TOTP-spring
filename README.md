
* `Securing your Spring App using 2FA` https://shinesolutions.com/2015/05/01/securing-your-spring-app-using-2fa
* `https://github.com/pablocaif/TOTP-spring-example`

* `Spring Security и кастомная аутентификация` http://pvtjoke.blogspot.com/2015/09/spring-security.html


#spring-boot-totp#
A simple example project with a bit of Angular-js and Spring that shows how to integrate TOTP with Spring Security.

There is also an example of how to create users and generate secrets for TOTP

##Requirements##

* Java 8
* PostgreSQL Database


 ================================================

* `Authentication` - Объект аутентификации
  В объекте аутентификации хранится информация полученная из запроса. 
  (По этому объекту менеджеры и провайдеры будут пытаться аутентифицировать пользователя в системе)

* `UserDetails` - Пользовательские данные
  В пользовательских данных хранится необходимая информация об аутентифицированном пользователе (имя пользователя, пароль и список выданных ролей)
  (Данный класс удобно расширять для хранения каких-то собственных данных о пользователе, которые понадобятся при обработке запроса...)

* `UserDetailsService` - Сервис пользовательских данных
  Этот сервис просто вытаскивает пользователя по полученным данным из хранилища (БД, памяти, properties-файла, ...) для иннициализации 'UserDetails'

* `AuthenticationProvider` - Провайдер аутентификации
  Провайдер пытается аутентифицировать пользователя.
  Если у провайдера получается аутентифицировать пользователя то он возвращает 'объект авторизации' (с пометкой об успешной аутентификации) 
  (обращается к методу 'loadUserByUsername' класса 'UserDetailsService' и проверяет предоставленные данные на соответствие полученным из БД

~~~~~~~~~~
AuthenticationProvider отвечает за загрузку данных пользователя и проверку учетных данных.
Во-первых, нам нужно настроить UserDetailsService, который отвечает за загрузку пароля, и предоставил роли для пользователя из постоянного хранилища...
~~~~~~~~~~

* `AuthenticationManager` - Менеджер аутентификации
  Задача менеджера обойти все известные ему провайдеры аутентификации, найти подходящий и попробовать аутентифицировать полученный объект аутентификации.
  (в него нужно будет добавить нашего провайдера аутентификации...)