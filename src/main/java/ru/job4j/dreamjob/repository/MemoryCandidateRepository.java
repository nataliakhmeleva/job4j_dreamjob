package ru.job4j.dreamjob.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryCandidateRepository implements CandidateRepository {
    private static final MemoryCandidateRepository INSTANCE = new MemoryCandidateRepository();

    private int nextId = 1;
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private MemoryCandidateRepository() {
        candidates.put(1, new Candidate(1, "Ivanov Nikolay", "No  experience", LocalDateTime.now()));
        candidates.put(2, new Candidate(2, "Shishkin Nikita", "Experience 1-2 years", LocalDateTime.now()));
        candidates.put(3, new Candidate(3, "Platonov Andrew", "Experience 2-3 years", LocalDateTime.now()));
        candidates.put(4, new Candidate(1, "Orlova Maria", "Experience 3-4 years", LocalDateTime.now()));
        candidates.put(5, new Candidate(2, "Letova Irina", "Experience 5-6 years", LocalDateTime.now()));
        candidates.put(6, new Candidate(3, "Vlasov Igor", "Experience 6+ years", LocalDateTime.now()));
    }

    public static MemoryCandidateRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public Candidate save(Candidate candidate) {
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }

    @Override
    public boolean update(Candidate candidate) {
        return false;
    }

    @Override
    public Optional<Candidate> findById(int id) {
        return Optional.empty();
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
