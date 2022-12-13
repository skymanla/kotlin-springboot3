package pray.ryan.trigger.domain

import jakarta.persistence.*
import jdk.jfr.Description
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.LocalDateTime

@Entity
@Table(name = "user")
@Description("user 테이블")
class User(
    id: String,
    pwd: String,
    nick: String,
    name: String?,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JdbcTypeCode(SqlTypes.INTEGER)
    @Column(name = "idx", nullable = false)
    var idx: Long? = null

    @Column(name = "id", nullable = false)
    @Description("id")
    var id: String = id

    @Column(name = "name", nullable = true)
    @Description("이름")
    var name: String? = name

    @Column(name = "pwd", nullable = false)
    @Description("비밀번호")
    var pwd: String = pwd

    @Column(name = "nick", nullable = false)
    @Description("닉네임")
    var nick: String = nick

    @Column(name = "login_dt", nullable = true)
    @Description("login 시간")
    var loginDt: LocalDateTime? = null

    @Column(name = "login_ip", nullable = true)
    @Description("login ip")
    var loginIp: String? = null

    @Column(name = "is_use", nullable = true)
    @Description("사용여부")
    var isUse: String = "N"

    @Column(name = "is_withdrawl", nullable = true)
    @Description("탈퇴여부")
    var isWithdrawl: String = "N"

    @Column(name = "withdrawl_dt", nullable = true)
    @Description("탈퇴시간")
    var withdrawlDt: LocalDateTime? = null

    @Column(name = "created_dt", nullable = false)
    @Description("생성시간")
    var createdDt: LocalDateTime = LocalDateTime.now()

    @Column(name = "updated_dt", nullable = true)
    @Description("변경시간")
    var updatedDt: LocalDateTime? = null

    @Column(name = "updated_ip", nullable = true)
    @Description("변경 ip")
    var updatedIp: String? = null
}