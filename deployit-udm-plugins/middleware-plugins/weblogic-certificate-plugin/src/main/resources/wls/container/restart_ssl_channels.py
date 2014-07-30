domainRuntime()


# check if function is available otherwise define it (workaround for OSB) 
if 'ensureStableContainerState' not in dir():
    def ensureStableContainerState(target, type):
        print "Going to check state of: ", type, " ", target.name
    
        if type == "Server": servers =[target.name]
        else: servers = target.servers
    
        for name in servers:
            for a in range(1, 5):
                status = cmo.lookupServerLifeCycleRuntime(name).getState()
                print type, ": ", name, ": ",status
    
                if (status not in ['SHUTTING_DOWN', 'STARTING']):
                    return
    
                print "# ", a, " Sleeping 2 seconds"
                java.lang.Thread.sleep(2000)
                
ensureStableContainerState(target, type)
        
if exists('/ServerRuntimes/' + target.name):
    print 'Restarting SSL Channels for ' + target.name
    cd ('/ServerRuntimes/' + target.name)
    cmo.restartSSLChannels()
    print 'Restarted SSL Channels for ' + target.name
else:
    print 'Restarting SSL Channels for ' + target.name + ' is not necessary, target is down'

sys.exit(0)