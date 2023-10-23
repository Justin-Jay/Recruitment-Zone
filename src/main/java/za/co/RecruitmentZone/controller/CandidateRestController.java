package za.co.RecruitmentZone.controller;

@RestController
@RequestMapping("/candidate/rest")
@CrossOrigin("*")
public class CandidateRestController {

    private final VacancyService vacancyService;
    private final CandidateService candidateService;
    private final Logger log = LoggerFactory.getLogger(CandidateRestController.class);

    public CandidateRestController(VacancyService vacancyService, CandidateService candidateService) {
        this.vacancyService = vacancyService;
        this.candidateService = candidateService;
    }

    @GetMapping("/vacancies")
    public ResponseEntity<List<Vacancy>> getActiveVacancies() {
        List<Vacancy> vacancies = vacancyService.getActiveVacancies();
        if (vacancies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(vacancies, HttpStatus.OK);
    }

    @PutMapping("/updateVacancyStatus/{id}/status")
    public ResponseEntity<Vacancy> updateVacancyStatus(@PathVariable Integer id, @RequestBody VacancyStatus newStatus) {
        try {
            vacancyService.updateStatus(id, newStatus);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateVacancyDetails/{id}")
    public ResponseEntity<Vacancy> updateVacancyDetails(@PathVariable Integer id, @RequestBody VacancyDTO vacancyDTO) {
        try {
            vacancyService.updateDetails(id, vacancyDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

