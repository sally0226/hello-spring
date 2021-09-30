package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    @Override
    Optional<Member> findByName(String name); // JpaRepository가 제공하지 않는 것
    // select m from Member m where m.name = ? 라는 쿼리를 짜줌

    // ex) findByNameAndId : select m from where m.name = ? and m.id = ? 이런식으로 짜줌 
}
