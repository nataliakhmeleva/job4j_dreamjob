package ru.job4j.dreamjob.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
public class MemoryCandidateRepository implements CandidateRepository {
    private int nextId = 1;
    private final Map<Integer, Candidate> candidates = new HashMap<>();

    private MemoryCandidateRepository() {
        save(new Candidate(0, "Ivanov Nikolay", "No  experience", LocalDateTime.now(), 1, 0));
        save(new Candidate(0, "Shishkin Nikita", "Experience 1-2 years", LocalDateTime.now(), 2, 0));
        save(new Candidate(0, "Platonov Andrew", "Experience 2-3 years", LocalDateTime.now(), 3, 0));
        save(new Candidate(0, "Orlova Maria", "Experience 3-4 years", LocalDateTime.now(), 3, 0));
        save(new Candidate(0, "Letova Irina", "Experience 5-6 years", LocalDateTime.now(), 2, 0));
        save(new Candidate(0, "Vlasov Igor", "Experience 6+ years", LocalDateTime.now(), 1, 0));
    }

    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId++);
        candidates.put(candidate.getId(), candidate);
        return candidate;
    }

    @Override
    public boolean deleteById(int id) {
        return candidates.remove(id) != null;
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidates.computeIfPresent(candidate.getId(), (id, oldСandidate) -> new Candidate(oldСandidate.getId(),
                candidate.getTitle(), candidate.getDescription(), candidate.getCreationDate(), candidate.getCityId(), candidate.getFileId())) != null;
    }

    @Override
    public Optional<Candidate> findById(int id) {
        return Optional.ofNullable(candidates.get(id));
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
