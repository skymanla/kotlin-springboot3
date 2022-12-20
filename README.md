# SpringBoot3 + SpringSecurity + JWT
> jwt로 스프링 시큐리티 사용하기

* 개발환경
  - openJdk 17
  - kotlin
  - mariadb


* spring 환경
  - spring boot 3.0
  - spring security
  - build는 gradle
  - war


## 알아두기
* spring boot 3.0 으로 오면서 google에서 많이 검색되는 WebSecurityAdapter 였나...
    이건 사용할 수가 없음(없어졌음)
* 아래처럼 사용해야함
```kotlin
@Bean
fun configure(http: HttpSecurity): SecurityFilterChain {
    // TODO http 적용시킬 내용
    return http.build()
}
```
* spring boot 버전이 올라가면서 구글에 있는 대부분의 spring security 소스들이 현 버전에 맞지 않아 작동되게 소스 변경
