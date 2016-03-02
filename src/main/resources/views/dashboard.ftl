<!DOCTYPE html>

<html>
	<head>
		<title>Dashboard</title>
		<link rel="stylesheet" type="text/css" href="/css/bcash.css" />
		<link rel="stylesheet" type="text/css" href="/css/dashboard.css" />
	</head>
	<body>
		<#setting datetime_format="dd/MM/yyyy hh:mm:ss a">	
    	<div id="dashboard">
    		<div id="dashboard-left">
    			<p>Welcome ${username}</p> 
    			<div id="dashboard-left-content">
    				<div>
    					<h4>Environments</h4>
   						<a href="/environments/new">New</a>
   						<#list environments as environment>
	    					<div>
	    						<a class="image-link" href="/environments/configure/${environment.id}">
	    							<img title="Configure" src="/img/setting.png">
								</a>
	    						<a class="image-link" href="/environments/duplicate/${environment.id}">
	    							<img title="Duplicate" src="/img/new-package.png">
								</a>
	    						<a class="image-link" href="/environments/remove/${environment.id}">
	    							<img title="Remove" src="/img/edit-delete.png">
								</a>
	    						${environment.id}
	    					</div>
    					</#list>
    				</div>
    				<div>
		    			<h4>Scripts</h4>						
    					<a href="/scripts/new">New</a>    					
						<#list scripts as script>	
							<div>							
	    						<a class="image-link" href="/scripts/configure/${script.id}">
	    							<img title="Configure" src="/img/setting.png">
								</a>
	    						<a class="image-link" href="/scripts/duplicate/${script.id}">
	    							<img title="Duplicate" src="/img/new-package.png">
								</a>
	    						<a class="image-link" href="/scripts/remove/${script.id}">
	    							<img title="Remove" src="/img/edit-delete.png">
								</a>
	    						<a class="image-link" href="/executions/new/${script.id}">
	    							<img title="Execute" src="/img/clock.png">
								</a>
	    						<a class="image-link" href="/executions/${script.id}">	    							
									${script.id}
								</a>
							</div>
						</#list>
		    		</div>
    			</div>
    		</div>
    		
    		<div id="dashboard-right">
				<p><a href="/logout">Logout</a></p>
    			<div id="dashboard-right-content">
    				<h4>Executions</h4>		
    				<a href="/masterExecutions/new">Execute</a>
    				<#list masterExecutions as masterExecution>
	    				<div>	    					
    						<a class="image-link" href="/executions/master/${masterExecution.id}">
    							<img title="Executions" src="/img/success.gif">
    							${masterExecution.date?datetime}
							</a>
	    				</div>
    				</#list>    				
    			</div>
    		</div>
		</div>
  	</body>
</html>
