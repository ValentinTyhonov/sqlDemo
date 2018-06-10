jQuery(document).ready(function() {

var PAGE_SIZE = 3;
var PAGE_NUM = 0;
var PAGE_MAX;

var employeesModel = {
	employees: ko.observableArray(),

	searchEmployee: function() {
	  loadData("/employee/search?page=" + PAGE_NUM + "&size=" + PAGE_SIZE + "&name=" + $("#searchInput").val());
	},

	removeEmployee: function() {
	  employeesModel.employees.remove(this);
	  employeeToRemove = JSON.parse(ko.toJSON(this));
	  performAJAX("/employee?id=" + employeeToRemove.id, "DELETE", "", function(data) {
	    $.jGrowl(data);
	    loadData("/employee?page=" + PAGE_NUM + "&size=" + PAGE_SIZE);
    });
  },

  editEmployee: function() {
    $("#updateBlock").show()
    var currentEmployee = ko.toJSON(this);
    performAJAX("/department", "GET", "", function(data) {
      var observableObject = $("#editedEmployeeModel")[0];
      ko.cleanNode(observableObject);
      ko.applyBindings(new editedEmployeeModel(JSON.parse(currentEmployee), JSON.parse(data)), observableObject);
    });
  },

  nextPage: function() {
    $("#previousPage").show();
    PAGE_NUM += 1;
    if(PAGE_NUM == PAGE_MAX) {
      $("#nextPage").hide();
    }
    loadData("/employee?page=" + PAGE_NUM + "&size=" + PAGE_SIZE);
  },

  previousPage: function() {
    $("#nextPage").show();
    PAGE_NUM -= 1;
    if(PAGE_NUM == 0) {
      $("#previousPage").hide();
    }
    loadData("/employee?page=" + PAGE_NUM + "&size=" + PAGE_SIZE);
  }

}

var Department = function(id, name) {
  this.id = id;
  this.name = name;
}

function singleEmployeeModel(id, name, active, departmentID, departmentName) {
	this.id = ko.observable(id);
	this.name = ko.observable(name);
	this.active = ko.observable(active);
	this.department = {
	  id: ko.observable(departmentID),
	  name: ko.observable(departmentName)
	}
}

function editedEmployeeModel(employeeData, departmentsData) {
  var departmentsArray = [];
  for(var i = 0; i < departmentsData.length; i++) {
    var department = new Department(departmentsData[i].id, departmentsData[i].name);
    departmentsArray.push(department);
  }

  var self = this;
  self.id = ko.observable(employeeData.id);
  self.name = ko.observable(employeeData.name);
  self.active = ko.observable(employeeData.active);
  self.availableDepartments = ko.observableArray(departmentsArray);
  self.department = {
    id: ko.observable(employeeData.department.id),
    name: ko.observable(employeeData.department.name)
  }

  self.updateEmployee = function() {
    $("#updateBlock").hide();
    performAJAX("/employee", "PUT", ko.toJSON(this), function(data) {
      $.jGrowl(data);
      loadData("/employee?page=" + PAGE_NUM + "&size=" + PAGE_SIZE);
    });
  }

  self.cancel = function() {
    $("#updateBlock").hide();
  }
}

function performAJAX(url, method, data, handleResponse) {
	$.ajax({
	  url: url,
	  type: method,
	  data: data,
	  contentType: 'application/json; charset=utf-8',
	  cache: false,
	  error: function (XMLHttpRequest, textStatus, errorThrown) {
	    $.jGrowl(XMLHttpRequest.responseText);
	  },
	  success: function (responseData, textStatus) {
	    handleResponse(responseData);
	  }
	});
}

function loadData(url) {
  performAJAX(url, "GET", "", function(data) {
    data = JSON.parse(data);
    PAGE_MAX = data.pages - 1;
    data = data.content;
    var preparedData = [];
    for(var i = 0; i < data.length; i++) {
	    var employee = new singleEmployeeModel(data[i].id, data[i].name, data[i].active, data[i].department.id, data[i].department.name);
	    preparedData.push(employee);
	  }

    employeesModel.employees(preparedData);
  });
}

loadData("/employee?page=" + PAGE_NUM + "&size=" + PAGE_SIZE);
ko.applyBindings(employeesModel, $("#employeesModel")[0]);


});
