package rs.raf.gym.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rs.raf.gym.dto.training_appointment.TrainingAppointmentCreateDto;
import rs.raf.gym.dto.training_appointment.TrainingAppointmentDto;
import rs.raf.gym.dto.training_appointment.TrainingAppointmentUpdateDto;
import rs.raf.gym.mapper.TrainingAppointmentMapper;
import rs.raf.gym.model.AppointmentStatus;
import rs.raf.gym.model.Gym;
import rs.raf.gym.model.GymTraining;
import rs.raf.gym.model.Training;
import rs.raf.gym.model.TrainingAppointment;
import rs.raf.gym.model.composite.GymTrainingComposite;
import rs.raf.gym.model.composite.TrainingAppointmentComposite;
import rs.raf.gym.repository.IAppointmentStatusRepository;
import rs.raf.gym.repository.IGymRepository;
import rs.raf.gym.repository.IGymTrainingRepository;
import rs.raf.gym.repository.ITrainingAppointmentRepository;
import rs.raf.gym.repository.ITrainingRepository;
import rs.raf.gym.service.ITrainingAppointmentService;
import rs.raf.gym.specification.TrainingAppointmentSpecification;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TrainingAppointmentService implements ITrainingAppointmentService {

    private final ITrainingAppointmentRepository repository;
    private final IGymRepository                 gymRepository;
    private final ITrainingRepository            trainingRepository;
    private final IGymTrainingRepository         gymTrainingRepository;
    private final IAppointmentStatusRepository   statusRepository;
    private final TrainingAppointmentMapper      mapper;

    @Override
    public Page<TrainingAppointmentDto> findAll(String gym, String training, LocalDate date, LocalTime time,
                                                Integer duration, String status, Pageable pageable) {
        TrainingAppointmentSpecification specification = new TrainingAppointmentSpecification(gym, training, date, time,
                                                                                              duration, status);

        return repository.findAll(specification.filter(), pageable)
                         .map(mapper::toTrainingAppointmentDto);
    }

    @Override
    public TrainingAppointmentDto create(TrainingAppointmentCreateDto createDto) {
        GymTraining gymTraining = findGymTraining(createDto.getGymName(), createDto.getTrainingName());

        AppointmentStatus appointmentStatus = statusRepository.findById(createDto.getStatusName())
                                                   .orElse(null);

        if (gymTraining == null || appointmentStatus == null) //TODO: Replace with exception
            return null;

        TrainingAppointment trainingAppointment = new TrainingAppointment();

        trainingAppointment.setTrainingAppointment(new TrainingAppointmentComposite(gymTraining.getGymTraining(), createDto.getDate(),
                                                                                    createDto.getTime()));
        trainingAppointment.setStatus(appointmentStatus);
        mapper.map(trainingAppointment, createDto);

        return mapper.toTrainingAppointmentDto(repository.save(trainingAppointment));
    }

    @Override
    public TrainingAppointmentDto update(TrainingAppointmentUpdateDto updateDto) {
        GymTraining gymTraining = findGymTraining(updateDto.getGymName(), updateDto.getTrainingName());

        if (gymTraining == null) //TODO: Replace with exception
            return null;

//        NOTE: DOES NOT WORK! For some reason ALWAYS returns null (-_-)
//        TrainingAppointment trainingAppointment = repository.findById(new TrainingAppointmentComposite(gymTraining.getGymTraining(),
//                                                                                                       updateDto.getDate(),
//                                                                                                       updateDto.getTime()))
//                                                            .orElse(null);

        //NOTE: its just work around
        //TODO: Remove in the future
        TrainingAppointmentSpecification specification = new TrainingAppointmentSpecification(updateDto.getGymName(),
                                                                                              updateDto.getTrainingName(),
                                                                                              updateDto.getDate(),
                                                                                              updateDto.getTime(),
                                                                                              null, null);

        //TODO: Remove in the future
        TrainingAppointment trainingAppointment = repository.findAll(specification.filter(), PageRequest.ofSize(1))
                                                            .getContent()
                                                            .get(0);

        if (trainingAppointment == null) //TODO: Replace with exception
            return null;

        AppointmentStatus appointmentStatus = statusRepository.findById(updateDto.getStatusName())
                                                              .orElse(null);

        trainingAppointment.setStatus(appointmentStatus);
        mapper.map(trainingAppointment, updateDto);

        return mapper.toTrainingAppointmentDto(repository.save(trainingAppointment));
    }

    private GymTraining findGymTraining(String gymName, String trainingName) {
        Gym gym = gymRepository.findById(gymName)
                               .orElse(null);
        Training training = trainingRepository.findById(trainingName)
                                              .orElse(null);

        if (gym == null || training == null) //TODO: Replace with exception
            return null;

        return gymTrainingRepository.findById(new GymTrainingComposite(gym, training))
                                   .orElse(null);
    }

}
