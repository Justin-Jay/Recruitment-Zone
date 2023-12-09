package za.co.RecruitmentZone.events.listener.Candidate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import za.co.RecruitmentZone.events.EventStore.Candidate.FileUploadEvent;
import za.co.RecruitmentZone.repository.CandidateRepository;


@Component
public class CandidateEventListener {

    // Listener for the CandidateAppliedEvent
    private final CandidateRepository candidateRepository;

    private final Logger log = LoggerFactory.getLogger(CandidateEventListener.class);

    public CandidateEventListener(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @EventListener
    public void onFileUploadEvent(FileUploadEvent event) {
        // Upon file upload
   /*     FileMetadata fileMetadata = extractMetadata(file);
        String fileId = generateUniqueId();

        luceneIndex.addDocument(fileId, fileMetadata);


        redisClient.set(fileId, fileMetadata);

*/
    }

     /* @EventListener

    public void onCandidateAppliedEvent(CandidateAppliedEvent event) {
        Application newApplication = event.getApplication();
        Integer candidateID = newApplication.getCandidateID();
        Integer vacancyID = newApplication.getApplicationVacancyID();
        String cvFilePth = newApplication.getCvFilePath();

        // Call the applyForVacancy method from CandidateService
*/


    // Searching with Lucene
    // List<SearchResult> searchResults = luceneIndex.search("search_query");

    // Retrieve file IDs from Lucene search results
    // List<String> fileIds = searchResults.stream().map(SearchResult::getFileId).collect(Collectors.toList());

    /*// Fetching Files from Google Cloud
for (String fileId : fileIds) {
    FileMetadata fileMetadata = redisClient.get(fileId);
    // Use file metadata to download the file from Google Cloud Storage
    downloadFile(fileMetadata);
}*/
}





