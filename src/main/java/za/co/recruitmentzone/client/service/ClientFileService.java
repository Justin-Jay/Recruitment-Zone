package za.co.recruitmentzone.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import za.co.recruitmentzone.client.entity.ClientFile;
import za.co.recruitmentzone.client.repository.ClientFileRepository;
import za.co.recruitmentzone.documents.DocumentService;
import za.co.recruitmentzone.documents.FileNotFoundException;

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


}
