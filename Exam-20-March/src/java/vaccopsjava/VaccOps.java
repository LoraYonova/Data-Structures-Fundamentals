package vaccopsjava;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VaccOps implements IVaccOps {

    private final List<Patient> patients;
    private final Map<Doctor, List<Patient>> doctorsAndPatients;

    public VaccOps() {

        this.patients = new ArrayList<>();
        this.doctorsAndPatients = new HashMap<>();
    }

    public void addDoctor(Doctor d) {

        if (doctorsAndPatients.containsKey(d)) {
            throw new IllegalArgumentException();
        }
        doctorsAndPatients.put(d, new ArrayList<>());


    }

    public void addPatient(Doctor d, Patient p) {

        if (!doctorsAndPatients.containsKey(d)) {
            throw new IllegalArgumentException();
        }

        if (patients.contains(p)) {
            throw new IllegalArgumentException();
        }

        doctorsAndPatients.get(d).add(p);
        patients.add(p);

    }

    public Collection<Doctor> getDoctors() {

        return doctorsAndPatients.keySet();

    }

    public Collection<Patient> getPatients() {
        return patients;

    }

    public boolean exist(Doctor d) {
        return doctorsAndPatients.containsKey(d);

    }

    public boolean exist(Patient p) {
        return patients.contains(p);

    }


    public Doctor removeDoctor(String name) {
        Doctor doctor = doctorsAndPatients
                .keySet().stream()
                .filter(d -> d.getName().equals(name))
                .findFirst()
                .orElse(null);

        if (doctor == null) {
            throw new IllegalArgumentException();
        }

        for (Patient patient : doctorsAndPatients.get(doctor)) {
            patients.remove(patient);
        }

        doctorsAndPatients.remove(doctor);

        return doctor;
    }

    public void changeDoctor(Doctor from, Doctor to, Patient p) {
        if (!doctorsAndPatients.containsKey(from) || !doctorsAndPatients.containsKey(to) || !patients.contains(p)) {
            throw new IllegalArgumentException();
        }

        if (doctorsAndPatients.get(from).remove(p)) {
            doctorsAndPatients.get(to).add(p);
        }

    }

    public Collection<Doctor> getDoctorsByPopularity(int populariry) {

        return doctorsAndPatients
                .keySet()
                .stream()
                .filter(d -> d.getPopularity() == populariry)
                .collect(Collectors.toList());

    }

    public Collection<Patient> getPatientsByTown(String town) {

        return patients
                .stream()
                .filter(p -> p.getTown().equals(town))
                .collect(Collectors.toList());

    }

    public Collection<Patient> getPatientsInAgeRange(int lo, int hi) {

        return patients
                .stream()
                .filter(p -> p.age >= lo && p.age <= hi)
                .collect(Collectors.toList());

    }

    public Collection<Doctor> getDoctorsSortedByPatientsCountDescAndNameAsc() {
        return doctorsAndPatients.entrySet().stream().sorted((f, s) -> {
                    if (s.getValue().size() - f.getValue().size() == 0) {
                        return f.getKey().getName().compareTo(s.getKey().getName());
                    }
                    return s.getValue().size() - f.getValue().size();
                })
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());


    }

    public Collection<Patient> getPatientsSortedByDoctorsPopularityAscThenByHeightDescThenByAge() {


//        List<Patient> patients = new ArrayList<>();
//
//        doctorsAndPatients.entrySet().stream().sorted((o1, o2) -> {
//
//            int result = o1.getKey().getPopularity() - o2.getKey().getPopularity();
//            if (result == 0) {
//                result = o2.getKey().getPopularity() - o1.getKey().getPopularity();
//            }
//            return result;
//
//        });
//
//        Comparator<Patient> patientComparator = Comparator.comparing(Patient::getHeight).reversed().thenComparing(Patient::getAge);
//        for (List<Patient> value : doctorsAndPatients.values()) {
//            value.sort(patientComparator);
//            patients.addAll(value);
//        }
//
//        return patients;
//
//    }
//}

        Comparator<Patient> comparator = Comparator.comparingInt(Patient::getHeight).reversed().thenComparingInt(Patient::getAge);
        TreeMap<Integer, List<Patient>> map = new TreeMap<>();
        this.doctorsAndPatients
                .keySet()
                .stream()
                .sorted(Comparator.comparingInt(Doctor::getPopularity))
                .forEach(doctor -> map.putIfAbsent(doctor.getPopularity(), new ArrayList<>()));

        for (Map.Entry<Doctor, List<Patient>> doctorsList : this.doctorsAndPatients.entrySet()) {
            int popularity = doctorsList.getKey().getPopularity();
            List<Patient> patientList = map.get(popularity);
            patientList.addAll(doctorsList.getValue());
        }

        List<Patient> patientList = new ArrayList<>();
        for (List<Patient> value : map.values()) {
            value.sort(comparator);
            patientList.addAll(value);
        }
        return patientList;
    }
}

