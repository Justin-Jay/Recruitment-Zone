package za.co.recruitmentzone.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import za.co.recruitmentzone.application.entity.Application;
import za.co.recruitmentzone.client.entity.ClientFile;
import za.co.recruitmentzone.client.repository.ClientFileRepository;
import za.co.recruitmentzone.documents.DocumentService;
import za.co.recruitmentzone.documents.FileNotFoundException;
import za.co.recruitmentzone.vacancy.entity.Vacancy;

import java.util.List;
import java.util.Optional;

@Service
public class ClientFileService implements DocumentService {
    private final Logger log = LoggerFactory.getLogger(ClientFileService.class);
    private final ClientFileRepository clientFileRepository;

    public ClientFileService(ClientFileRepository clientFileRepository) {
        this.clientFileRepository = clientFileRepository;
    }

    public ClientFile saveClientFile(ClientFile clientFile) {
        return clientFileRepository.save(clientFile);
    }

    @Override
    public ClientFile findFileById(Long id) {
        return clientFileRepository.findById(id).orElseThrow(() -> new FileNotFoundException("Could not find client file with id: " + id));
    }

    @Override
    public String getDocumentLocation(String id){
        Long fileID = Long.valueOf(id);
        String docLocation = "";
        Optional<ClientFile> optionalClientFile = clientFileRepository.findById(fileID);
        if(optionalClientFile.isPresent()){
            ClientFile file = optionalClientFile.get();
            docLocation = file.getDocumentLocation();
        }
        return docLocation;
    }

    @Override
    public List<ClientFile> getFiles(Long clientID) {
        log.info("<--- getFiles {} --->",clientID);
        return clientFileRepository.findClientFileById(clientID);
    }

    public List<ClientFile> loadVacancyDocs(Long vacancyID) {
        log.info("<--- loadVacancyDocs {} --->",vacancyID);
        return clientFileRepository.findClientFileByVacancyID(vacancyID);
    }



    public Page<ClientFile> findPaginatedVacancyDocs(int pageNo, int pageSize, String sortField, String sortDirection,Vacancy vacancy){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
        return clientFileRepository.findAllByVacancy(pageable,vacancy);
    }
}
