console.log("in students.js");

const baseURL = "http://localhost:8081";

const viewStudentModal = document.getElementById('view_student_modal');
console.log(viewStudentModal);
const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// instance options object
const instanceOptions = {
  id: 'view_student_modal',
  override: true
};

// Initialize studentModal
const studentModal = new Modal(viewStudentModal, options, instanceOptions);

console.log(studentModal);

async function loadStudentdata(studentId) {
  console.log(studentId);
  console.log("inside loadStudentData...");

  try {
    console.log("inside try block..");
    const studentData = await (await fetch(`${baseURL}/school/student/${studentId}`)).json();

    console.log(studentData);
    document.querySelector("#student_name").innerHTML = studentData.studentName;
    document.querySelector("#student_email").innerHTML = studentData.studentEmail;
    document.querySelector("#student_address").innerHTML = studentData.student_Address;
    document.querySelector("#student_phone").innerHTML = studentData.studentPhoneNumber;
    document.querySelector("#student_mdm_id").innerHTML = studentData.student_mdm_id; 
    document.querySelector("#student_school").innerHTML = studentData.school.school_Name; 
    document.querySelector("#student_roll_no").innerHTML = studentData.student_Roll_No;
    document.querySelector("#student_Age").innerHTML = studentData.student_Age;
    document.querySelector("#student_Parents_Name").innerHTML = studentData.student_Parents_Name;

    console.log("displaying..");
    openStudentModal(); // Call this after studentModal is initialized
  } catch (error) {
    console.log("catch block of loadStudentData");
    console.log("Error: ", error);
  }
}

function openStudentModal() {
  studentModal.show();
}

function closeStudentModal() {
  studentModal.hide();
}


async function deleteStudent(id) {

  console.log("inside deleteContact() with id: "+id);
      Swal.fire({
        title: "Do you want to delete the Student?",
        text: "You won't be able to revert this!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, delete it!"
      }).then((result) => {
        if (result.isConfirmed) {
          Swal.fire({
            title: "Deleted!",
            text: "Your file has been deleted.",
            icon: "success"
           
          });
          const deleteURL=`${baseURL}/school/student/delete-student/` +id;
            window.location.replace(deleteURL);
            console.log("student deleted Successfully with id: "+id);
        }
      });
}