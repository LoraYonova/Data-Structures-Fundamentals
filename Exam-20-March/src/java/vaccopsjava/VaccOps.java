package vaccopsjava;

import java.util.*;

public class VaccOps implements IVaccOps {
    //    private Map<Doctor, Patient> doctors;
    private Set<Doctor> doctors;
    private List<Patient> patients;

    public VaccOps() {
        this.doctors = new HashSet<>();
        this.patients = new ArrayList<>();
    }

    public void addDoctor(Doctor d) {

        if (doctors.contains(d)) {
            throw new IllegalArgumentException();
        }
        this.doctors.add(d);
    }

    public void addPatient(Doctor d, Patient p) {
        Patient patient = new Patient(p.name, p.height, p.age, p.town);

        Doctor doctor = doctors.stream().filter(doc -> doc.getName().equals(d.getName())).findFirst().orElse(null);
        if (doctor == null) {
            throw new IllegalArgumentException();
        }
        patients.add(patient);
    }

    public Collection<Doctor> getDoctors() {
        if (doctors.size() == 0) {
            return new ArrayList<>();
        }
        return doctors;
    }

    public Collection<Patient> getPatients() {
        if (patients.size() == 0) {
            return new ArrayList<>();
        }
        return patients;
    }

    public boolean exist(Doctor d) {
        return false;
    }

    public boolean exist(Patient p) {
        return false;
    }


    public Doctor removeDoctor(String name) {
        return null;
    }

    public void changeDoctor(Doctor from, Doctor to, Patient p) {
    }

    public Collection<Doctor> getDoctorsByPopularity(int populariry) {
        return null;
    }

    public Collection<Patient> getPatientsByTown(String town) {
        return null;
    }

    public Collection<Patient> getPatientsInAgeRange(int lo, int hi) {
        return null;
    }

    public Collection<Doctor> getDoctorsSortedByPatientsCountDescAndNameAsc() {
        return null;
    }

    public Collection<Patient> getPatientsSortedByDoctorsPopularityAscThenByHeightDescThenByAge() {
        return null;
    }

}
