package servlet.singeleton;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerFactorySingeleton {
  private static EntityManagerFactory emf;

  private EntityManagerFactorySingeleton() {
  }

  public static EntityManagerFactory getEntityManagerFactory() {
    if (emf == null) {
      synchronized (EntityManagerFactorySingeleton.class) {
        if (emf == null) {
          emf = Persistence.createEntityManagerFactory("thePersistenceUnit");
        }
      }
    }
    return emf;
  }

  public static void closeEntityManagerFactory() {
    if (emf != null) {
      emf.close();
    }
  }
}
