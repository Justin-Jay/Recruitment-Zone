package za.co.recruitmentzone.candidate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import za.co.recruitmentzone.candidate.entity.Candidate;
import za.co.recruitmentzone.candidate.entity.CandidateFile;
import za.co.recruitmentzone.candidate.entity.CandidateNote;
import za.co.recruitmentzone.client.repository.CandidateFileRepository;
import za.co.recruitmentzone.documents.DocumentService;
import za.co.recruitmentzone.documents.FileNotFoundException;
import za.co.recruitmentzone.util.enums.CandidateDocumentType;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateFileService implements DocumentService {
    private final Logger log = LoggerFactory.getLogger(CandidateFileService.class);
    private final CandidateFileRepository candidateFileRepository;

    public CandidateFileService(CandidateFileRepository candidateFileRepository) {
        this.candidateFileRepository = candidateFileRepository;
    }

    public CandidateFile   saveCandidateFile(CandidateFile candidateFile) {
        return candidateFileRepository.save(candidateFile);
    }

    @Override
    public CandidateFile findFileById(Long id) {
        return candidateFileRepository.findById(id).orElseThrow(() -> new FileNotFoundException("Could not find candidate file with id: " + id));
    }

    @Override
    public String getDocumentLocation(String id) {
        Long fileID = Long.valueOf(id);
        String docLocation = "";
        Optional<CandidateFile> optionalCandidateFile = candidateFileRepository.findById(fileID);
        if (optionalCandidateFile.isPresent()) {
            CandidateFile file = optionalCandidateFile.get();
            docLocation = file.getDocumentLocation();
        }
        return docLocation;
    }

    @Override
    public List<CandidateFile> getFiles(Long candidateID) {
        log.info("<--- getCandidateFiles {} --->", candidateID);
        return candidateFileRepository.findByCandidateID(candidateID);
    }


    public CandidateDocumentType getDocumentType(String id) {
        Long fileID = Long.valueOf(id);
        CandidateDocumentType documentType = null;
        Optional<CandidateFile> optionalCandidateFile = candidateFileRepository.findById(fileID);
        if (optionalCandidateFile.isPresent()) {
            CandidateFile file = optionalCandidateFile.get();
            documentType = file.getCandidateDocumentType();
        }
        return documentType;
    }


    public Page<CandidateFile> findPaginatedCandidateDocs(int pageNo, int pageSize, String sortField, String sortDirection, Candidate candidate){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
        return candidateFileRepository.findAllByCandidate(pageable,candidate);
    }
}
