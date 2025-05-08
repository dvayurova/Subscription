package ru.webrise.subscription.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.webrise.subscription.model.Subscription;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query(value = """
                SELECT
                           s.name as name,
                           COUNT(*) as count  
                       FROM subscriptions s
                       GROUP BY s.name
                       ORDER BY count DESC
                       LIMIT 3
            """, nativeQuery = true)
    List<SubscriptionProjection> findTop3PopularSubscriptions();
}

