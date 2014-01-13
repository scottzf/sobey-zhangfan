<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<div class="navbar navbar-default navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">CMOP v2.0</a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
          
            
            <li class="dropdown "active"">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">网络管理 <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="#">Switch</a></li>
                <li><a href="#">Firewall</a></li>
                <li><a href="#">DNS</a></li>
                <li><a href="#">LoadBalancer</a></li>
              </ul>
            </li>
            
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>