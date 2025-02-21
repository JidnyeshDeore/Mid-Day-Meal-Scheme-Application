
console.log("in supplier.js");

const baseURL = "http://localhost:8081";

const viewsupplierModal = document.getElementById('view_supplier_modal');
console.log("it is view supplier"+viewsupplierModal);
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
  id: 'view_supplier_modal',
  override: true
};

// Initialize supplierModal
const supplierModal = new Modal(viewsupplierModal, options, instanceOptions);

console.log(supplierModal);

async function loadsupplierdata(supplierId) {
  console.log(supplierId);
  console.log("inside loadsupplierdata...");

  try {
    console.log("inside try block..");
    const supplierData = await (await fetch(`${baseURL}/admin/supplier/${supplierId}`)).json();

    console.log(supplierData);
    document.querySelector("#supplier_Name").innerHTML = supplierData.supplierName;
    document.querySelector("#supplier_Email").innerHTML = supplierData.supplierEmail;
    document.querySelector("#supplier_Address").innerHTML = supplierData.supplierAddress;
    document.querySelector("#supplier_Phone_Number").innerHTML = supplierData.supplierContactNumber;
    // document.querySelector("#supplier_status").innerHTML = supplierData.isActive ? "Active" : "Inactive"; 
    document.querySelector("#supplier_FSSAIRegistrationNumber").innerHTML = supplierData.FSSAIRegistrationNumber;

    // Populate assigned meal requests
    const mealRequestList = document.querySelector("#supplier_mealRequest");
    mealRequestList.innerHTML = ""; // Clear previous data
    if ( supplierData.mealRequests && supplierData.mealRequests.length > 0) {
      supplierData.mealRequests.forEach((mealRequest) => {
        const listItem = document.createElement("li");
        listItem.innerHTML = `
          <strong>${mealRequest.mealRequestId}:</strong> <br>
          Assigned to ${mealRequest.school?.school_Name || "Unknown School"}, <br>
          Delivery Date: ${mealRequest.deliveryDate}, <br>
          Status: ${mealRequest.status}
        `;
        mealRequestList.appendChild(listItem);
      });
    } else {
      mealRequestList.innerHTML = "<li>No assigned meal requests.</li>";
    }

    console.log("displaying..");
    opensupplierModal(); // Call this after supplierModal is initialized
  } catch (error) {
    console.log("catch block of loadsupplierdata");
    console.log("Error: ", error);
  }
}


function opensupplierModal() {
  supplierModal.show();
}

function closesupplierModal() {
  supplierModal.hide();
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
//           const deleteURL=`${baseURL}/supplier/student/delete-student/` +id;
//             window.location.replace(deleteURL);
//             console.log("student deleted Successfully with id: "+id);
//         }
//       });
// }




// $(document).ready(function () {
//     $('#example').DataTable({
//         ajax: {
//             url: '/admin/view-suppliers', // Spring Boot API endpoint
//             dataSrc: '' // Use this if the data is a flat array
//         },
//         columns: [
//             { data: 'supplier_Name' },         // Maps to the "name" field in your supplier entity
//             { data: 'supplier_Address' },     // Maps to the "position" field
//             { data: 'supplier_Email' },       // Maps to the "office" field
//             { data: 'supplier_Phone_Number' },          // Maps to the "age" field
//             { data: 'mdm_Id' },    // Maps to the "startDate" field
//             { data: 'supplier_mdm_Instructor' }        // Maps to the "salary" field
//         ],
//         responsive: true
//     });
// });

