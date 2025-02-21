
console.log("in school.js");

const baseURL = "http://localhost:8081";

const viewSchoolModal = document.getElementById('view_school_modal');
console.log("it is view school"+viewSchoolModal);
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
  id: 'view_school_modal',
  override: true
};

// Initialize schoolModal
const schoolModal = new Modal(viewSchoolModal, options, instanceOptions);

console.log(schoolModal);

async function loadSchooldata(school_Id) {
  console.log(school_Id);
  console.log("inside loadSchooldata...");

  try {
    console.log("inside try block..");
    const schoolData = await (await fetch(`${baseURL}/admin/school/${school_Id}`)).json();

    console.log(schoolData);
    document.querySelector("#school_Name").innerHTML = schoolData.school_Name;
    document.querySelector("#school_Email").innerHTML = schoolData.school_Email;
    document.querySelector("#school_Address").innerHTML = schoolData.school_Address;
    document.querySelector("#school_Phone_Number").innerHTML = schoolData.school_Phone_Number;
    document.querySelector("#school_mdm_id").innerHTML = schoolData.mdm_Id; 
    document.querySelector("#school_strength").innerHTML = schoolData.school_Strength;
    document.querySelector("#school_mdm_Instructor").innerHTML = schoolData.school_mdm_Instructor;
    

    console.log("displaying..");
    openschoolModal(); // Call this after schoolModal is initialized
  } catch (error) {
    console.log("catch block of loadSchooldata");
    console.log("Error: ", error);
  }
}

function openschoolModal() {
  schoolModal.show();
}

function closeSchoolModal() {
  schoolModal.hide();
}


// async function deleteStudent(id) {

//   console.log("inside deleteContact() with id: "+id);
//       Swal.fire({
//         title: "Do you want to delete the Student?",
//         text: "You won't be able to revert this!",
//         icon: "warning",
//         showCancelButton: true,
//         confirmButtonColor: "#3085d6",
//         cancelButtonColor: "#d33",
//         confirmButtonText: "Yes, delete it!"
//       }).then((result) => {
//         if (result.isConfirmed) {
//           Swal.fire({
//             title: "Deleted!",
//             text: "Your file has been deleted.",
//             icon: "success"
           
//           });
//           const deleteURL=`${baseURL}/school/student/delete-student/` +id;
//             window.location.replace(deleteURL);
//             console.log("student deleted Successfully with id: "+id);
//         }
//       });
// }




// $(document).ready(function () {
//     $('#example').DataTable({
//         ajax: {
//             url: '/admin/view-schools', // Spring Boot API endpoint
//             dataSrc: '' // Use this if the data is a flat array
//         },
//         columns: [
//             { data: 'school_Name' },         // Maps to the "name" field in your School entity
//             { data: 'school_Address' },     // Maps to the "position" field
//             { data: 'school_Email' },       // Maps to the "office" field
//             { data: 'school_Phone_Number' },          // Maps to the "age" field
//             { data: 'mdm_Id' },    // Maps to the "startDate" field
//             { data: 'school_mdm_Instructor' }        // Maps to the "salary" field
//         ],
//         responsive: true
//     });
// });

