
console.log("in meal.js");

const baseURL = "http://localhost:8081";
let currentMealRequestId = null; // Global variable to store mealRequestId

const viewMealModal = document.getElementById("view_meal_modal");
const options = {
  placement: "bottom-right",
  backdrop: "dynamic",
  backdropClasses: "bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40",
  closable: true,
  onHide: () => console.log("modal is hidden"),
  onShow: () => console.log("modal is shown"),
};

const mealModal = new Modal(viewMealModal, options);

async function loadMealData(mealRequestId) {
  currentMealRequestId = mealRequestId; // Save the ID for further actions
  console.log("Loading meal data for ID:", mealRequestId);

  try {
    const mealData = await (await fetch(`${baseURL}/admin/view-meal/${mealRequestId}`)).json();
    console.log(mealData);
    if (!mealData) throw new Error("Meal data not found");

    document.querySelector("#Meal_School_Name").innerHTML = mealData.school.school_Name;
    document.querySelector("#Meal_School_Email").innerHTML = mealData.school.school_Email;
    document.querySelector("#Meal_Status").innerHTML = mealData.status;
    document.querySelector("#Meal_Contact_Person").innerHTML = mealData.contactPerson;
    document.querySelector("#Meal_Contact_Number").innerHTML = mealData.contactPhone;
    document.querySelector("#Meal_School_MDM_ID").innerHTML = mealData.school.mdm_Id;
    document.querySelector("#Meal_Assigned_Supplier").innerHTML = mealData.assignedSupplier?.supplierName || "Unknown Supplier";
    const mealItemsElement = document.querySelector("#Meal_mealItems");
    mealItemsElement.innerHTML = mealData.mealItems?.length
      ? mealData.mealItems
          .map((item) => `<div>- ${item.name}: ${item.quantity} units</div>`)
          .join("")
      : "No items available";

    openMealModal();
  } catch (error) {
    console.error("Error loading meal data:", error);
  }
}

function openMealModal() {
  mealModal.show();
}

function closeMealModal() {
  mealModal.hide();
}

// Load suppliers
// async function loadSuppliers() {
//   const supplierDropdown = document.getElementById("supplierDropdown");
//   supplierDropdown.innerHTML = '<option value="" disabled selected>Select Supplier</option>';

//   try {
//     const response = await fetch(`${baseURL}/admin/suppliers`);
//     console.log(response);
//     const suppliers = await response.json();

//     if (!suppliers || suppliers.length === 0) {
//       supplierDropdown.innerHTML = '<option value="" disabled>No suppliers available</option>';
//       return;
//     }

//     suppliers.forEach((supplier) => {
//       const option = document.createElement("option");
//       option.value = supplier.supplierId;
//       option.textContent = supplier.supplierName;
//       supplierDropdown.appendChild(option);
//     });
//   } catch (error) {
//     console.error("Error loading suppliers:", error);
//     supplierDropdown.innerHTML = '<option value="" disabled>Error loading suppliers</option>';
//   }
// }


// Assign supplier
// async function assignSupplier() {
//   const supplierId = document.getElementById("supplierDropdown").value;

//   if (!supplierId || !currentMealRequestId) {
//     alert("Please select a supplier!");
//     return;
//   }

//   try {
//     const response = await fetch(
//       `${baseURL}/admin/assign-supplier/${currentMealRequestId}?supplierId=${supplierId}`,
//       { method: "POST" }
//     );

//     if (response.ok) {
//       alert("Supplier assigned successfully!");
//       loadMealData(currentMealRequestId);
//     } else {
//       const error = await response.text();
//       alert("Error assigning supplier: " + error);
//     }
//   } catch (error) {
//     console.error("Error assigning supplier:", error);
//   }
// }

// Update status
async function updateMealRequestStatus() {
  const status = document.getElementById("statusDropdown").value;

  if (!status || !currentMealRequestId) {
    alert("Please select a valid status!");
    return;
  }

  try {
    const response = await fetch(
      `${baseURL}/admin/update-status/${currentMealRequestId}?status=${status}`,
      { method: "PATCH" }
    );

    if (response.ok) {
      alert("Status updated successfully!");
      loadMealData(currentMealRequestId);
    } else {
      const error = await response.text();
      alert("Error updating status: " + error);
    }
  } catch (error) {
    console.error("Error updating status:", error);
  }
}
