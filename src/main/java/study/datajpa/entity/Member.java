package study.datajpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id","username","age"}) //Team은 빠져야한다 -> 순환참조로 무한루프에 빠질 위험성
@NamedQuery(
        name="Member.findByUsername",
        query = "select m from Member m where m.username= :username"
)
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long Id;
    private String username;
    private int age;
    @ManyToOne(fetch = FetchType.LAZY) //ToOne 관계는 전부 Lazy로 변경할것! [Default가 즉시로딩이라 바꿔줘야함]
    @JoinColumn(name = "team_id")
    Team team;

    public Member(String username) {
        this.username = username;
    }

    public Member(String username, int age, Team team) {
        this.username=username;
        this.age=age;
        if(team != null){
            //예외가 발생되거나 무시하거나
            changeTeam(team);
        }

    }

    public Member(String username, int age) {
        this.username=username;
        this.age=age;
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }

}
