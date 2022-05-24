package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("teamA");
            member.setAge(10);

            member.setTeam(team);

            em.persist(member);

            em.flush();
            em.clear();

            String query = "select  m from Member  m left join m.team t on t.name = 'teamA'";
            List<Member> result = em.createQuery(query,Member.class)
                    .getResultList();

            System.out.println("result.size = " + result.size());
            for (Member member1 : result) {
                System.out.println("member1 = " + member1);
            }

            System.out.println("result = " + result.size());


            tx.commit();
        }catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }
        emf.close();
    }

}
