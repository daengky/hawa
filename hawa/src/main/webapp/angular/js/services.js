'use strict';

/* Services */

var services = angular.module('homeAutomation.services', ['ngResource']);

services.value('version', '%VERSION%');

services.factory('systemState', ['$resource', function($resource) {
	return $resource('rest/systemstate', {}, {
		query : {
			method : 'GET',
			isArray : true
		}
	});
} ]);

services.factory('rooms', ['$resource', function($resource) {
	return $resource('rest/rooms', {}, {
		query : {
			method : 'GET',
			isArray : true
		}
	});
} ]);

services.factory('weather', ['$resource', function($resource) {
	return $resource('rest/weather', {}, {
		query : {
			method : 'GET',
			isArray : true
		}
	});
} ]);